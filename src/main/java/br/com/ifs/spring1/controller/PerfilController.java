package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.Perfil;
import br.com.ifs.spring1.service.IPerfilService;
import br.com.ifs.spring1.service.impl.PerfilServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("perfil")
@RequiredArgsConstructor
public class PerfilController {

    private final IPerfilService perfilService;
    @GetMapping
    public Object getAll() {
        return perfilService.getAll();
    }
    @GetMapping(value = "/byNome/{nome}")
    public Object getByLikeNome(@PathVariable(name = "nome") String nome) {
        return perfilService.getByNomeLike(nome);
    }

    @PostMapping
    public Object cadastrtar(@RequestBody Perfil perfil) {
        return perfilService.cadastrar(perfil);
    }

    @DeleteMapping(value = "/{id}")
    public Object excluir(@PathVariable(name = "id") Integer perNrId) {
        perfilService.excluir(perNrId);
        return "Perfil excluído com sucesso!";
    }
}
