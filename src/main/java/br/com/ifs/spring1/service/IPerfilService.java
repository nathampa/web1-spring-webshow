package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.Perfil;

import java.util.List;

public interface IPerfilService {

    List<Perfil> getAll();
    List<Perfil> getByNomeLike(String nome);

    Perfil cadastrar(Perfil perfil);

    void excluir(Integer perNrId);
}
