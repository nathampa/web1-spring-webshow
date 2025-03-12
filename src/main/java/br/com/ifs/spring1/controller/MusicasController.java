package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.Musicas;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.service.IArquivoService;
import br.com.ifs.spring1.service.IMusicasService;
import br.com.ifs.spring1.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("musicas")
@RequiredArgsConstructor
public class MusicasController {
    private final IMusicasService musicasService;
    private final IUsuarioService usuarioService;
    private final IArquivoService arquivoService;

    @GetMapping
    public Object getAll(){
        return musicasService.getAll();
    }

    @PostMapping("/cadastrarMusica")
    public Object cadastrar(@RequestParam("titulo") String titulo, @RequestPart("arquivoPdf") MultipartFile arquivoPdf, Authentication authentication) {
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            //Salva arquivo e obtem o caminho
            String caminhoArquivo = arquivoService.salvarArquivo(arquivoPdf);

            //Cria a entidade Musicas e salva o caminho no banco
            Musicas musica = new Musicas();
            musica.setTitulo(titulo);
            musica.setArquivoPdf(caminhoArquivo); //Caminho do arquivo

            musicasService.cadastrar(musica, usuario);

            return ResponseEntity.ok("Música cadastrada com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    @DeleteMapping("/excluirMusica/{idMusica}")
    public Object excluir(@PathVariable(name = "idMusica") Integer idMusica) {
        try {
            musicasService.excluir(idMusica);

            return ResponseEntity.ok("Música excluida com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/minhasMusicas")
    public Object getMusicasDoUsuario(Authentication authentication) {
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());
            List<Musicas> musicas = musicasService.getMusicasDoUsuario(usuario.getIdUsuario());

            if (musicas.isEmpty()) {
                return ResponseEntity.ok("Nenhuma música encontrada para este usuário.");
            }

            return ResponseEntity.ok(musicas);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao buscar músicas.");
        }
    }

    @GetMapping("/baixarMusica/{idMusica}")
    public Object baixarMusica(@PathVariable Integer idMusica) {
        try {
            // Buscar a música pelo ID
            Musicas musica = musicasService.findByIdMusica(idMusica);

            if (musica == null) {
                return ResponseEntity.notFound().build();
            }

            // Obter o caminho do arquivo PDF
            String caminhoArquivo = musica.getArquivoPdf();

            // Criar um recurso a partir do arquivo
            Resource resource = arquivoService.carregarComoResource(caminhoArquivo);

            // Verificar se o arquivo existe
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }

            // Configurar o cabeçalho para download do arquivo
            String nomeArquivo = musica.getTitulo() + ".pdf";
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + nomeArquivo + "\"")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(resource);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
