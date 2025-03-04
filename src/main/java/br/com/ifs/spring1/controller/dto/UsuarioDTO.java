package br.com.ifs.spring1.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioDTO {
    private Integer idUsuario;
    private String nome;
    private String login;
}
