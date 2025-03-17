package br.com.ifs.spring1.config;

import br.com.ifs.spring1.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new LiveSessionWebSocketHandler(jwtUtil), "/live-session")
                .setAllowedOrigins("http://localhost:5173", "*");
    }
}

class LiveSessionWebSocketHandler extends TextWebSocketHandler {
    private final JwtUtil jwtUtil;
    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private Integer currentRepertorioId; // Armazena o repertório atual
    private Map<String, Object> currentSongData; // Armazena a música atual

    public LiveSessionWebSocketHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String query = session.getUri().getQuery();
        String token = null;
        if (query != null && query.contains("token=")) {
            token = query.split("token=")[1].split("&")[0];
        }

        if (token != null && jwtUtil.validateToken(token)) {
            sessions.put(session.getId(), session);
            System.out.println("Conexão estabelecida com token válido: " + token);
            session.sendMessage(new TextMessage("{\"type\": \"connection\", \"message\": \"Conexão estabelecida\"}"));

            // Enviar estado atual para o novo cliente, se existir
            if (currentRepertorioId != null) {
                String sessionInfo = String.format(
                        "{\"type\": \"session-info\", \"repertorioId\": %d%s}",
                        currentRepertorioId,
                        currentSongData != null ? ", \"songData\": " + new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(currentSongData) : ""
                );
                session.sendMessage(new TextMessage(sessionInfo));
            }
        } else {
            System.out.println("Token inválido ou ausente");
            session.close(CloseStatus.NOT_ACCEPTABLE);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Mensagem recebida: " + payload);
        Map<String, Object> data = new com.fasterxml.jackson.databind.ObjectMapper().readValue(payload, Map.class);
        String type = (String) data.get("type");

        if ("start-session".equals(type)) {
            currentRepertorioId = (Integer) data.get("repertorioId");
            broadcastMessage(payload);
        } else if ("update-song".equals(type)) {
            currentSongData = (Map<String, Object>) data.get("songData");
            broadcastMessage(payload);
        } else if ("join-session".equals(type)) {
            // Enviar estado atual ao músico que acabou de entrar
            if (currentRepertorioId != null) {
                String sessionInfo = String.format(
                        "{\"type\": \"session-info\", \"repertorioId\": %d%s}",
                        currentRepertorioId,
                        currentSongData != null ? ", \"songData\": " + new com.fasterxml.jackson.databind.ObjectMapper().writeValueAsString(currentSongData) : ""
                );
                session.sendMessage(new TextMessage(sessionInfo));
            }
        } else {
            broadcastMessage(payload); // Ecoar outras mensagens
        }
    }

    private void broadcastMessage(String message) throws Exception {
        for (WebSocketSession s : sessions.values()) {
            if (s.isOpen()) {
                s.sendMessage(new TextMessage(message));
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session.getId());
        System.out.println("Conexão fechada: " + status);
    }
}