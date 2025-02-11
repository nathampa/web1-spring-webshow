package br.com.ifs.spring1.security;

import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.UsuarioRepository;
import java.util.Collections;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByLogin(username);
        if (usuario == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }

        return User.builder()
                .username(usuario.getLogin())
                .password(usuario.getSenha()) // Agora a senha está criptografada
                .roles("USER")
                .build();
    }
}
