package br.edu.fatec.estagio.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fatec.estagio.model.Empresa;

@Repository 
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {

    Optional<Empresa> findByCnpj (String cnpj);

    List<Empresa> findByNomeEmpresaContainingIgnoreCase (String nome);

    boolean existsContratosById(Long empresaId);

}
