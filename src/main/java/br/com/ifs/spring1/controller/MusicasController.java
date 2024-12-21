package br.com.ifs.spring1.controller;

import br.com.ifs.spring1.model.Musicas;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.service.IMusicasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("musicas")
@RequiredArgsConstructor
public class MusicasController {
    private final IMusicasService musicasService;

    @GetMapping
    public Object getAll(){
        return musicasService.getAll();
    }

    @PostMapping("/cadastrarMusica")
    public Object cadastrar(@RequestBody Musicas musica) {
        try {
            musicasService.cadastrar(musica);
            return ResponseEntity.ok("Música cadastrada com sucesso!");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }
}