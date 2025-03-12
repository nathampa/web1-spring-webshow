package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.Musicas;
import br.com.ifs.spring1.model.Usuario;

import java.util.List;

public interface IMusicasService {
    List<Musicas> getAll();
    Musicas cadastrar(Musicas musica, Usuario usuario);
    void excluir(Integer idMusica);
    List<Musicas> getMusicasDoUsuario(Integer idUsuario);
    Musicas findByIdMusica(Integer idMusica);
}
