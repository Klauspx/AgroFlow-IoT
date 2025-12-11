package br.unifap.pi.projetoirrigacao.dto;

import lombok.Data;

@Data
public class LeituraArduinoRequest {
    private String nomeSensor; // Ex: "Região A"
    private String tipoLeitura; // "umidade" ou "temperatura"
    private double valor;
}