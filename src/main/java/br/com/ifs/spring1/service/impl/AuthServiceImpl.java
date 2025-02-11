package br.com.ifs.spring1.service.impl;

import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.UsuarioRepository;
import br.com.ifs.spring1.security.JwtUtil;
import br.com.ifs.spring1.service.IAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String autenticar(String login, String senha) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, senha));
        return jwtUtil.generateToken(login);
    }

    @Override
    public void registrarUsuario(Usuario usuario) {
        if (usuarioRepository.findByLogin(usuario.getLogin()) != null) {
            throw new IllegalArgumentException("Usuário já existe");
        }

        usuario.setSenha(passwordEncoder.encode(usuario.getSenha())); // Criptografa senha
        usuarioRepository.save(usuario);
    }
}
