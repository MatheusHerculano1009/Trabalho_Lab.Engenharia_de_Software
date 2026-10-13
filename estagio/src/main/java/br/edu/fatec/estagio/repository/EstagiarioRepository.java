package br.edu.fatec.estagio.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fatec.estagio.model.Estagiario;

@Repository
public interface EstagiarioRepository extends JpaRepository<Estagiario, Long> {

    Optional<Estagiario> findByRa(String ra);

    List<Estagiario> findByNomeContainingIgnoreCase(String nome);

    // Busca por nome E curso em ordem alfabética
    List<Estagiario> findByNomeContainingIgnoreCaseAndCursoContainingIgnoreCaseOrderByNomeAsc(String nome, String curso);

}
