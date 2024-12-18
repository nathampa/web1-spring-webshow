package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.UsuarioRepository;
import br.com.ifs.spring1.service.IBandaService;
import br.com.ifs.spring1.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("banda")
@RequiredArgsConstructor
public class BandaController {
    private final IBandaService bandaService;
    private final IUsuarioService usuarioService;

    @GetMapping
    public Object getAll(){
        return bandaService.getAll();
    }

    @PostMapping("/cadastrarBanda")
    public Object cadastrar(@RequestBody Banda banda, HttpSession sessao) {
        try {
            Usuario usuario = usuarioService.getAuthenticatedUser(sessao);

            bandaService.cadastrar(banda, usuario);

            return ResponseEntity.ok("Banda cadastrada com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/excluirBanda/{idBanda}")
    public Object excluir (@PathVariable(name = "idBanda") Integer idBanda, HttpSession sessao){
        try {
            Usuario usuario = usuarioService.getAuthenticatedUser(sessao);

            bandaService.excluir(idBanda, usuario);

            return ResponseEntity.ok("Banda excluida com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}