package br.edu.fatec.estagio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatec.estagio.dto.EstagiarioDTO;
import br.edu.fatec.estagio.model.Empresa;
import br.edu.fatec.estagio.model.Estagiario;
import br.edu.fatec.estagio.repository.EstagiarioRepository;

@Service
public class EstagiarioService {

    @Autowired
    private EstagiarioRepository estagiarioRepository;

    public List<Estagiario> listarTodos() {
        return estagiarioRepository.findAll();
    }

    public List<Estagiario> buscarPorNome(String nome) {
        return estagiarioRepository.findByNomeContainingIgnoreCase(nome);
    }

    public Optional<Estagiario> buscarPorRa(String ra) {
        return estagiarioRepository.findByRa(ra);
    }

    public Optional<Estagiario> buscarPorId(Long id) {
        return estagiarioRepository.findById(id);
    }

    public boolean deletarPorId(Long id) {
        Optional<Estagiario> estagiario = estagiarioRepository.findById(id);
        if (estagiario.isPresent() && !estagiario.get().getContratos().isEmpty()) {
            return false;
        }
        estagiarioRepository.deleteById(id);
        return true;
    }

    // Se o RA foi informado, ele tem prioridade (busca exata);
    // senão filtra por nome e curso. Valores nulos viram "" e não restringem a busca.
    public List<Estagiario> filtrar(String ra, String nome, String curso) {
        String raBusca = normalizar(ra);
        if (!raBusca.isEmpty()) {
            Optional<Estagiario> estagiario = estagiarioRepository.findByRa(raBusca);
            if (estagiario.isPresent()) {
                return List.of(estagiario.get());
            }
            return List.of();
        }
        return estagiarioRepository.findByNomeContainingIgnoreCaseAndCursoContainingIgnoreCaseOrderByNomeAsc(
                normalizar(nome), normalizar(curso));
    }

    // Retorna a mensagem de erro se o RA já pertence a outro aluno; retorna null se estiver livre.
    public String validarRaDuplicado(EstagiarioDTO dto) {
        if (dto.getRa() == null || dto.getRa().isBlank()) {
            return null;
        }
        Optional<Estagiario> estagiarioExiste = estagiarioRepository.findByRa(dto.getRa().trim());
        if (estagiarioExiste.isPresent()) {
            Estagiario estagiarioBanco = estagiarioExiste.get();
            if (dto.getId() == null) {
                return "Esse aluno já possui cadastro com esse RA!";
            }
            if (!estagiarioBanco.getId().equals(dto.getId())) {
                return "Este RA já pertence a outro aluno cadastrado!";
            }
        }
        return null;
    }

    public void salvarEstagiario(EstagiarioDTO dto) {
        Estagiario estagiario;
        if (dto.getId() != null) {
            estagiario = estagiarioRepository.findById(dto.getId()).orElse(new Estagiario());
        } else {
            estagiario = new Estagiario();
        }

        estagiario.setNome(dto.getNome().trim());
        estagiario.setRa(dto.getRa().trim());
        estagiario.setTelefone(vazioParaNull(dto.getTelefone()));
        estagiario.setEmail(dto.getEmail().trim());
        estagiario.setCurso(dto.getCurso().trim());
        estagiario.setSemestre(vazioParaNull(dto.getSemestre()));
        estagiario.setPeriodo(dto.getPeriodo());
        estagiario.setTurno(vazioParaNull(dto.getTurno()));
        estagiario.setEscolaOrigem(dto.getEscolaOrigem().trim());
        estagiario.setEnderecoEscola(dto.getEnderecoEscola().trim());
        estagiario.setVezesVestibular(dto.getVezesVestibular());
        estagiario.setFaixaRenda(vazioParaNull(dto.getFaixaRenda()));
        estagiario.setEtnia(vazioParaNull(dto.getEtnia()));
        estagiario.setGenero(vazioParaNull(dto.getGenero()));

        estagiarioRepository.save(estagiario);
    }

    public EstagiarioDTO converterParaDto(Estagiario estagiario) {
        return EstagiarioDTO.builder().id(estagiario.getId()).nome(estagiario.getNome())
                .ra(estagiario.getRa()).telefone(estagiario.getTelefone()).email(estagiario.getEmail())
                .curso(estagiario.getCurso()).semestre(estagiario.getSemestre()).periodo(estagiario.getPeriodo())
                .turno(estagiario.getTurno()).escolaOrigem(estagiario.getEscolaOrigem())
                .enderecoEscola(estagiario.getEnderecoEscola()).vezesVestibular(estagiario.getVezesVestibular())
                .faixaRenda(estagiario.getFaixaRenda()).etnia(estagiario.getEtnia()).genero(estagiario.getGenero())
                .build();
    }

    private String normalizar(String valor) {
        if (valor == null) {
            return "";
        }
        return valor.trim();
    }

    private String vazioParaNull(String valor) {
        if (valor == null || valor.isBlank()) {
            return null;
        }
        return valor.trim();
    }

}
