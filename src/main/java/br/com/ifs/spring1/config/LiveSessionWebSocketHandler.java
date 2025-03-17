package br.com.ifs.spring1.config;

import br.com.ifs.spring1.security.JwtUtil;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class LiveSessionWebSocketHandler extends TextWebSocketHandler {
    private final JwtUtil jwtUtil;
    private final Map<Integer, BandSession> bandSessions = new ConcurrentHashMap<>();

    public LiveSessionWebSocketHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    private static class BandSession {
        List<WebSocketSession> sessions = new ArrayList<>();
        Integer repertorioId;
        Map<String, Object> currentSongData;
        String leaderSessionId;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = getTokenFromSession(session);
        if (token == null || !jwtUtil.validateToken(token)) {
            System.out.println("Token inválido ou ausente");
            session.close(CloseStatus.NOT_ACCEPTABLE);
            return;
        }

        System.out.println("Conexão estabelecida para sessionId: " + session.getId());
        session.sendMessage(new TextMessage("{\"type\": \"connection\", \"message\": \"Conexão estabelecida\"}"));
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Mensagem recebida: " + payload);
        Map<String, Object> data = new com.fasterxml.jackson.databind.ObjectMapper().readValue(payload, Map.class);
        String type = (String) data.get("type");
        Integer bandaId = (Integer) data.get("bandaId");

        if (bandaId == null) {
            session.sendMessage(new TextMessage("{\"type\": \"error\", \"message\": \"bandaId não fornecido\"}"));
            return;
        }

        BandSession bandSession = bandSessions.get(bandaId);
        if ("start-session".equals(type)) {
            if (bandSession != null && bandSession.leaderSessionId != null) {
                session.sendMessage(new TextMessage("{\"type\": \"error\", \"message\": \"Sessão já iniciada por outro cliente\"}"));
                return;
            }
            bandSession = bandSessions.computeIfAbsent(bandaId, k -> new BandSession());
            synchronized (bandSession.sessions) {
                if (!bandSession.sessions.contains(session)) {
                    bandSession.sessions.add(session);
                }
                bandSession.leaderSessionId = session.getId();
                bandSession.repertorioId = (Integer) data.get("repertorioId");
            }
            broadcastMessage(bandaId, payload);
        } else if ("join-session".equals(type)) {
            if (bandSession == null || bandSession.leaderSessionId == null) {
                session.sendMessage(new TextMessage("{\"type\": \"error\", \"message\": \"Nenhuma sessão ativa para esta banda\"}"));
                session.close(CloseStatus.NOT_ACCEPTABLE);
                return;
            }
            synchronized (bandSession.sessions) {
                if (!bandSession.sessions.contains(session)) {
                    bandSession.sessions.add(session);
                }
            }
            String sessionInfo = String.format(
                    "{\"type\": \"session-info\", \"repertorioId\": %d, \"bandaId\": %d%s}",
                    bandSession.repertorioId,
                    bandaId,
                    bandSession.currentSongData != null ? ", \"songData\": " + new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(bandSession.currentSongData) : ""
            );
            session.sendMessage(new TextMessage(sessionInfo));
        } else if ("update-song".equals(type)) {
            if (bandSession == null || !session.getId().equals(bandSession.leaderSessionId)) {
                session.sendMessage(new TextMessage("{\"type\": \"error\", \"message\": \"Apenas o líder pode atualizar a música\"}"));
                return;
            }
            bandSession.currentSongData = (Map<String, Object>) data.get("songData");
            broadcastMessage(bandaId, payload);
        } else if ("end-session".equals(type)) {
            if (bandSession == null || !session.getId().equals(bandSession.leaderSessionId)) {
                session.sendMessage(new TextMessage("{\"type\": \"error\", \"message\": \"Apenas o líder pode encerrar a sessão\"}"));
                return;
            }
            broadcastMessage(bandaId, "{\"type\": \"session-ended\", \"bandaId\": " + bandaId + ", \"message\": \"Sessão encerrada pelo líder\"}");
            closeSession(bandaId);
        }
    }

    private void broadcastMessage(Integer bandaId, String message) throws Exception {
        BandSession bandSession = bandSessions.get(bandaId);
        if (bandSession != null) {
            synchronized (bandSession.sessions) {
                for (WebSocketSession s : bandSession.sessions) {
                    if (s.isOpen()) {
                        s.sendMessage(new TextMessage(message));
                    }
                }
            }
        }
    }

    private void closeSession(Integer bandaId) throws Exception {
        BandSession bandSession = bandSessions.get(bandaId);
        if (bandSession != null) {
            synchronized (bandSession.sessions) {
                List<WebSocketSession> sessionsToClose = new ArrayList<>(bandSession.sessions);
                for (WebSocketSession s : sessionsToClose) {
                    if (s.isOpen()) {
                        s.close(CloseStatus.NORMAL);
                    }
                }
                bandSession.sessions.clear();
                bandSessions.remove(bandaId);
                System.out.println("Sessão completamente encerrada e removida para bandaId: " + bandaId);
            }
        } else {
            System.out.println("Nenhuma sessão encontrada para bandaId: " + bandaId + " ao tentar fechar");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        for (Map.Entry<Integer, BandSession> entry : bandSessions.entrySet()) {
            Integer bandaId = entry.getKey();
            BandSession bandSession = entry.getValue();
            synchronized (bandSession.sessions) {
                if (bandSession.sessions.remove(session)) {
                    if (session.getId().equals(bandSession.leaderSessionId) && !bandSession.sessions.isEmpty()) {
                        broadcastMessage(bandaId, "{\"type\": \"session-ended\", \"bandaId\": " + bandaId + ", \"message\": \"Líder desconectado, sessão encerrada\"}");
                        closeSession(bandaId);
                    } else if (bandSession.sessions.isEmpty()) {
                        bandSessions.remove(bandaId);
                        System.out.println("Sessão vazia removida para bandaId: " + bandaId);
                    }
                    break;
                }
            }
        }
        System.out.println("Conexão fechada para sessionId: " + session.getId() + ", status: " + status);
    }

    private String getTokenFromSession(WebSocketSession session) {
        String query = session.getUri().getQuery();
        if (query != null && query.contains("token=")) {
            return query.split("token=")[1].split("&")[0];
        }
        return null;
    }
}