package br.unifap.pi.projetoirrigacao.repository;

import br.unifap.pi.projetoirrigacao.model.HistoricoAtividade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface HistoricoAtividadeRepository extends JpaRepository<HistoricoAtividade, Long> {
    // Busca os 100 eventos mais recentes
    List<HistoricoAtividade> findTop100ByOrderByDataHoraDesc();
}