package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.Musicas;
import br.com.ifs.spring1.model.Usuario;

import java.util.List;

public interface IMusicasService {
    List<Musicas> getAll();
    Musicas cadastrar(Musicas musica);
    void excluir(Integer idMusica);
}
