package br.unifap.pi.projetoirrigacao.model;

import java.time.LocalDateTime;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@NoArgsConstructor
@Entity
@Table(name = "historico_atividades")
public class HistoricoAtividade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp // O Spring define a data/hora automaticamente
    private LocalDateTime dataHora;

    private String acao; // "ON" ou "OFF"

    private String sensorNome; // Qual sensor causou a ação

    public HistoricoAtividade(String acao, String sensorNome) {
        this.acao = acao;
        this.sensorNome = sensorNome;
    }
}