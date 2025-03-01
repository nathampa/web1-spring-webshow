package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.BandaUsuario;
import br.com.ifs.spring1.model.BandaUsuarioId;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.UsuarioRepository;
import br.com.ifs.spring1.service.IBandaService;
import br.com.ifs.spring1.service.IUsuarioService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("banda")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class BandaController {
    private final IBandaService bandaService;
    private final IUsuarioService usuarioService;
    private final UsuarioRepository usuarioRepository;

    @GetMapping
    public Object getAll(){
        return bandaService.getAll();
    }

    @GetMapping("/listarMusicos")
    public Object getAllMusicos(){
        return bandaService.getAllMusicos();
    }

    @GetMapping("/getBandasUsuario")
    public Object getBandasUsuario(Authentication authentication){
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            List<Banda> bandas = bandaService.findBandasByUsuarioIdOrderByNome(usuario.getIdUsuario());

            return ResponseEntity.ok(bandas);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/cadastrarBanda")
    public Object cadastrar(@RequestBody Banda banda, Authentication authentication) {
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            bandaService.cadastrar(banda, usuario);

            return ResponseEntity.ok("Banda cadastrada com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @PostMapping("/adicionarUsuario")
    public Object adicionarUsuario(@RequestBody BandaUsuario bandaUsuario, Authentication authentication){
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            bandaService.adicionarUsuario(bandaUsuario, usuario);

            return ResponseEntity.ok("Usuario adicionado com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping("/removerUsuario")
    public Object removerUsuario(@RequestBody BandaUsuario bandaUsuario, Authentication authentication){
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            bandaService.removerUsuario(bandaUsuario, usuario);

            return ResponseEntity.ok("Usuario removido com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/excluirBanda/{idBanda}")
    public Object excluir (@PathVariable(name = "idBanda") Integer idBanda, Authentication authentication){
        try {
            Usuario usuario = usuarioService.findByLogin(authentication.getName());

            bandaService.excluir(idBanda, usuario);

            return ResponseEntity.ok("Banda excluida com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
