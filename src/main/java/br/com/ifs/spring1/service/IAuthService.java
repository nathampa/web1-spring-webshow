package br.com.ifs.spring1.service;

import br.com.ifs.spring1.model.Usuario;

public interface IAuthService {
    String autenticar(String login, String senha);
    void registrarUsuario(Usuario usuario);
}
