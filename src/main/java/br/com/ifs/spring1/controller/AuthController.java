package br.com.ifs.spring1.controller;


import br.com.ifs.spring1.controller.dto.JwtResponse;
import br.com.ifs.spring1.controller.dto.LoginRequest;
import br.com.ifs.spring1.model.Usuario;
import br.com.ifs.spring1.repository.UsuarioRepository;
import br.com.ifs.spring1.security.JwtUtil;
import br.com.ifs.spring1.service.IAuthService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:5173")
public class AuthController {
    private final UsuarioRepository usuarioRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final IAuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.autenticar(loginRequest.getLogin(), loginRequest.getSenha());
            return ResponseEntity.ok(new JwtResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas");
        }
    }

    @PostMapping("/registrar")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        try {
            authService.registrarUsuario(usuario);
            return ResponseEntity.ok("Usuário cadastrado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PostMapping("/logout")
    public String logout(HttpSession sessao) {
        sessao.invalidate();
        return "Logout realizado com sucesso!";
    }

}
