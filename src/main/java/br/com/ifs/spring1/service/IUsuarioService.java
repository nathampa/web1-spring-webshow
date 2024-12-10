package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.Usuario;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> getAll();
    //List<Usuario> getByNomeLike(String nome);

    Usuario cadastrar(Usuario usuario);

    void excluir(Integer idUsuario);
}
