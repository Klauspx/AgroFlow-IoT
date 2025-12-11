package br.unifap.pi.projetoirrigacao.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String senha;
}