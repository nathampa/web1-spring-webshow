package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.*;

import java.util.List;
import java.util.Map;

public interface IRepertoriosService {
    List<Repertorios> getAll();
    List<RepertorioMusica> getAllMusicas();
    List<Repertorios> getRepertoriosByBanda(Integer idBanda);
    List<Map<String, Object>> getMusicasByRepertorio(Integer idRepertorio);
    List<Musicas> getMusicasAtivasByRepertorio(Integer idRepertorio);
    Repertorios cadastrar(Repertorios repertorio, Usuario usuario);
    RepertorioMusica adicionarMusica(RepertorioMusica repertorioMusica, Usuario usuario);
    void desativarMusica(RepertorioMusica repertorioMusica, Usuario usuario);
    void excluirMusica(RepertorioMusica repertorioMusica, Usuario usuario);
    void ativarMusica(RepertorioMusica repertorioMusica, Usuario usuario);
    void excluir(Integer idRepertorio, Usuario usuario);
    void atualizarOrdem(Integer idRepertorio, List<Integer> idsMusicas);
}
