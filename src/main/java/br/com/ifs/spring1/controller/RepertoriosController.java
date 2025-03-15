package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.controller.dto.AtualizarOrdemDTO;
import br.com.ifs.spring1.model.Musicas;
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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/listarRepertorios/{idBanda}")
    public Object getRepertoriosByBanda(@PathVariable Integer idBanda){
        List<Repertorios> repertorios = repertoriosService.getRepertoriosByBanda(idBanda);

        if (repertorios.isEmpty()){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(repertorios);
     }

     @GetMapping("/listarMusicasRepertorio/{idRepertorio}")
     public Object getMusicasByRepertorio(@PathVariable Integer idRepertorio){
        List<Musicas> musicas = repertoriosService.getMusicasByRepertorio(idRepertorio);

         if (musicas.isEmpty()){
             return ResponseEntity.notFound().build();
         }

         return ResponseEntity.ok(musicas);
     }


    @PostMapping("/cadastrarRepertorio")
    public Object cadastrar(@RequestBody Repertorios repertorio, Authentication authentication) {
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            repertoriosService.cadastrar(repertorio,usuario);

            return ResponseEntity.ok("Repertório adicionado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/adicionarMusica")
    public Object adicionarMusica(@RequestBody RepertorioMusica repertorioMusica, Authentication authentication){
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            repertoriosService.adicionarMusica(repertorioMusica,usuario);

            return ResponseEntity.ok("Música adicionada no repertório!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/ativarMusica")
    public Object ativarMusica(@RequestBody RepertorioMusica repertorioMusica, Authentication authentication){
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            repertoriosService.ativarMusica(repertorioMusica,usuario);

            return ResponseEntity.ok("Música adicionada ao repertório!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PutMapping("/{idRepertorio}/ordem")
    public ResponseEntity<Void> atualizarOrdem(@PathVariable Integer idRepertorio, @RequestBody AtualizarOrdemDTO dto) {
        repertoriosService.atualizarOrdem(idRepertorio, dto.getIdsMusicas());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/excluirMusica")
    public Object excluirMusica(@RequestBody RepertorioMusica repertorioMusica, Authentication authentication){
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            repertoriosService.excluirMusica(repertorioMusica,usuario);

            return ResponseEntity.ok("Música excluida do repertório!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/excluir/{idRepertorio}")
    public Object excluir(@PathVariable(name = "idRepertorio") Integer idRepertorio, Authentication authentication) {
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            repertoriosService.excluir(idRepertorio,usuario);

            return ResponseEntity.ok("Repertório excluido com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

}
