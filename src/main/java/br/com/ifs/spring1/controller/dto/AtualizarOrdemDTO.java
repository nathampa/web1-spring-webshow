package br.com.ifs.spring1.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class AtualizarOrdemDTO {
    private List<Integer> idsMusicas;
}
