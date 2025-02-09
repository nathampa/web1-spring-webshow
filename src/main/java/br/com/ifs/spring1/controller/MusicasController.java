package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.Musicas;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.service.IMusicasService;
import br.com.ifs.spring1.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("musicas")
@RequiredArgsConstructor
public class MusicasController {
    private final IMusicasService musicasService;
    private final IUsuarioService usuarioService;

    @GetMapping
    public Object getAll(){
        return musicasService.getAll();
    }

    @PostMapping("/cadastrarMusica")
    public Object cadastrar(@RequestBody Musicas musica, HttpSession sessao) {
        try {
            Usuario usuario = usuarioService.getAuthenticatedUser(sessao);

            musicasService.cadastrar(musica, usuario);

            return ResponseEntity.ok("Música cadastrada com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
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
}
