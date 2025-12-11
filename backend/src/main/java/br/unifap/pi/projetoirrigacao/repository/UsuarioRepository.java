package br.unifap.pi.projetoirrigacao.repository;

import br.unifap.pi.projetoirrigacao.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Comando mágico para buscar por email
    Optional<Usuario> findByEmail(String email);
}