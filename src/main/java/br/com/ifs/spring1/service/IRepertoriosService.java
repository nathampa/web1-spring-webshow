package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.BandaUsuario;
import br.com.ifs.spring1.model.Repertorios;
import br.com.ifs.spring1.model.Usuario;

import java.util.List;

public interface IRepertoriosService {
    List<Repertorios> getAll();

    Repertorios cadastrar(Repertorios repertorio, Usuario usuario);

    void excluir(Integer idRepertorio, Usuario usuario);

}
