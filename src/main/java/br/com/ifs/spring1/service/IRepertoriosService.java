package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.*;

import java.util.List;

public interface IRepertoriosService {
    List<Repertorios> getAll();
    List<RepertorioMusica> getAllMusicas();
    List<Repertorios> getRepertoriosByBanda(Integer idBanda);
    Repertorios cadastrar(Repertorios repertorio, Usuario usuario);
    RepertorioMusica adicionarMusica(RepertorioMusica repertorioMusica, Usuario usuario);
    void excluirMusica(RepertorioMusica repertorioMusica, Usuario usuario);
    void ativarMusica(RepertorioMusica repertorioMusica, Usuario usuario);
    void excluir(Integer idRepertorio, Usuario usuario);

}
