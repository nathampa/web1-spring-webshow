package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.Banda;
import br.com.ifs.spring1.model.Usuario;

import java.util.List;

public interface IBandaService {
    List<Banda> getAll();
    //List<Banda> getByNomeLike(String nome);

    Banda cadastrar(Banda banda, Usuario usuario);

    void excluir(Integer idBanda, Usuario usuario);
}
