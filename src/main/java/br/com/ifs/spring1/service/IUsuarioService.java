package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.Usuario;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface IUsuarioService {
    List<Usuario> getAll();
    //List<Usuario> getByNomeLike(String nome);
    Usuario cadastrar(Usuario usuario);
    Usuario findByLogin(String login);
    void excluir(Integer idUsuario);
    Usuario getAuthenticatedUser(HttpSession sessao);
}
