package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.service.IUsuarioService;
import lombok.RequiredArgsConstructor;
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
        usuarioService.cadastrar(usuario);
        return "Perfil cadastrado com sucesso";
    }

    @DeleteMapping(value = "/{id}")
    public Object excluir(@PathVariable(name = "id") Integer perNrId) {
        usuarioService.excluir(perNrId);
        return "Perfil excluído com sucesso!";
    }
}
