package br.unifap.pi.projetoirrigacao.dto;

import lombok.Data;
import java.util.List;

@Data
public class ConfiguracaoSensorRequest {
    private Long id;
    private boolean ativo;
    // O front-end envia uma lista desses quando clica em "Salvar"
}