package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final IUsuarioService usuarioService;
    @GetMapping
    public Object getAll() {
        return usuarioService.getAll();
    }

    /*@GetMapping(value = "/byNome/{nome}")
    public Object getByLikeNome(@PathVariable(name = "nome") String nome) {
        return usuarioService.getByNomeLike(nome);
    }*/

    @PostMapping
    public Object cadastrar(@RequestBody Usuario usuario) {
        try {
            usuarioService.cadastrar(usuario);
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @DeleteMapping(value = "/{id}")
    public Object excluir(@PathVariable(name = "id") Integer idUsuario) {
        usuarioService.excluir(idUsuario);
        return "Usuário excluído com sucesso!";
    }
}
