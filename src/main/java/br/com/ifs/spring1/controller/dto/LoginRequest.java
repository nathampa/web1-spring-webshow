package br.com.ifs.spring1.controller.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class LoginRequest {
    private String login;
    private String senha;
}
