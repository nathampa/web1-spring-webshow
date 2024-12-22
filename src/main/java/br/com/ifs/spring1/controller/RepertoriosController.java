package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.RepertorioMusica;
import br.com.ifs.spring1.model.Repertorios;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.service.IBandaService;
import br.com.ifs.spring1.service.IRepertoriosService;
import br.com.ifs.spring1.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("repertorios")
@RequiredArgsConstructor
public class RepertoriosController {
    private final IRepertoriosService repertoriosService;
    private final IBandaService bandaService;
    private final IUsuarioService usuarioService;
    @GetMapping
    public Object getAll(){
        return repertoriosService.getAll();
    }

    @GetMapping("/listarMusicas")
    public Object getAllMusicas(){
        return repertoriosService.getAllMusicas();
    }

    @PostMapping("/cadastrarRepertorio")
    public Object cadastrar(@RequestBody Repertorios repertorio, HttpSession sessao) {
        try {
            Usuario usuario = usuarioService.getAuthenticatedUser(sessao);

            repertoriosService.cadastrar(repertorio,usuario);

            return ResponseEntity.ok("Repertório adicionado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/adicionarMusica")
    public Object adicionarMusica(@RequestBody RepertorioMusica repertorioMusica, HttpSession sessao){
        try {
            Usuario usuario = usuarioService.getAuthenticatedUser(sessao);

            repertoriosService.adicionarMusica(repertorioMusica,usuario);

            return ResponseEntity.ok("Música adicionada no repertório!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/ativarMusica")
    public Object ativarMusica(@RequestBody RepertorioMusica repertorioMusica, HttpSession sessao){
        try {
            Usuario usuario = usuarioService.getAuthenticatedUser(sessao);

            repertoriosService.ativarMusica(repertorioMusica,usuario);

            return ResponseEntity.ok("Música adicionada ao repertório!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/excluirMusica")
    public Object excluirMusica(@RequestBody RepertorioMusica repertorioMusica, HttpSession sessao){
        try {
            Usuario usuario = usuarioService.getAuthenticatedUser(sessao);

            repertoriosService.excluirMusica(repertorioMusica,usuario);

            return ResponseEntity.ok("Música excluida do repertório!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{idRepertorio}")
    public Object excluir(@PathVariable(name = "idRepertorio") Integer idRepertorio,HttpSession sessao) {
        try {
            Usuario usuario = usuarioService.getAuthenticatedUser(sessao);

            repertoriosService.excluir(idRepertorio,usuario);

            return ResponseEntity.ok("Repertório excluido com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
