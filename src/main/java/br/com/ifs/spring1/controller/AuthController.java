package br.com.ifs.spring1.controller;


import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public String login(@RequestParam String login, @RequestParam String senha, HttpSession sessao){
        Optional<Usuario> usuarioOptional = usuarioRepository.findByLogin(login);

        if (usuarioOptional.isPresent()){
            Usuario usuario = usuarioOptional.get();

            if (usuario.getSenha().equals(senha)){
                sessao.setAttribute("authenticatedUser", login);
                return "Login realizado com sucesso!";
            }else{
                return "Senha incorreta!";
            }
        }else{
            return "Usuário não encontrado!";
        }
    }

    @PostMapping("/logout")
    public String logout(HttpSession sessao) {
        sessao.invalidate();
        return "Logout realizado com sucesso!";
    }

}
