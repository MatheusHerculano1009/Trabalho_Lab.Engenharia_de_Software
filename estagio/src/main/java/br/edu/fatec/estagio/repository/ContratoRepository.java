package br.edu.fatec.estagio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fatec.estagio.model.Contrato;

@Repository
public interface ContratoRepository extends JpaRepository<Contrato, Long> {

    // Filtra pelo nome do estagiário e da empresa, do contrato mais recente para o mais antigo
    List<Contrato> findByEstagiarioNomeContainingIgnoreCaseAndEmpresaNomeEmpresaContainingIgnoreCaseOrderByInicioContratoDesc(
            String nomeEstagiario, String nomeEmpresa);

}
