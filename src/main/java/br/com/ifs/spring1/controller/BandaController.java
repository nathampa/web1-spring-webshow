package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.service.IBandaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("banda")
@RequiredArgsConstructor
public class BandaController {
    private final IBandaService bandaService;

    @GetMapping
    public Object getAll(){
        return bandaService.getAll();
    }

    @PostMapping(value = "/{idUsuario}")
    public Object cadastrar(@RequestBody Banda banda, Usuario usuario){
        bandaService.cadastrar(banda, usuario);
        return "Banda cadastrada com sucesso!";
    }

    @DeleteMapping(value = "/{idBanda}/{idUsuario}")
    public Object excluir (@PathVariable(name = "idBanda") Integer idBanda, @PathVariable(name = "idUsuario") Integer idUsuario){
        bandaService.excluir(idBanda, idUsuario);
        return "Banda excluida com sucesso!";
    }
}
