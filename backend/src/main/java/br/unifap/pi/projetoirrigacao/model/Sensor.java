package br.unifap.pi.projetoirrigacao.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "sensores")
public class Sensor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private Double umidade;
    private Double temperatura;

    private boolean ativo; // Sistema Funcionando (Verde) ou Manutenção (Vermelho)
    private Double limiteAtivacao;

    // --- NOVO CAMPO: Memória do estado automático ---
    private boolean ultimaBombaLigada = false;

    public Sensor(String nome, Double limiteAtivacao) {
        this.nome = nome;
        this.limiteAtivacao = limiteAtivacao;
        this.umidade = 0.0;
        this.temperatura = 0.0;
        this.ativo = true; // Começa ativado
        this.ultimaBombaLigada = false;
    }
}