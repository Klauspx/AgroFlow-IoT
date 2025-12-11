package br.unifap.pi.projetoirrigacao.repository;

import br.unifap.pi.projetoirrigacao.model.Sensor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Importe esta classe
import java.util.Optional;

public interface SensorRepository extends JpaRepository<Sensor, Long> {
    // Permite buscar um sensor pelo nome (ex: "Sensor 1")
    Optional<Sensor> findByNome(String nome);

    // ADICIONE ESTA LINHA:
    // Força o Spring a buscar todos os sensores ordenados pelo ID
    List<Sensor> findAllByOrderByIdAsc();
}