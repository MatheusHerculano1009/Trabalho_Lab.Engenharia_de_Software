package br.edu.fatec.estagio.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.edu.fatec.estagio.dto.EstagiarioDTO;
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

    public void deletarPorId(Long id) {
        estagiarioRepository.deleteById(id);
    }

    public String validarRaDuplicado(EstagiarioDTO dto) {
        Optional<Estagiario> estagiarioExiste = estagiarioRepository.findByRa(dto.getRa());
        if (estagiarioExiste.isPresent()) {
            Estagiario estagiarioBanco  = estagiarioExiste.get();
            if (dto.getId() == null) {
                return "Esse aluno já possui cadastro com esse RA!";
            }
            if (!estagiarioBanco.getId().equals(dto.getId())) {
                return "Este RA já pertence a outro aluno cadastrado!";
            }
        }
        return null;
    }

    public void salvarEstagiario (EstagiarioDTO dto) {
        Estagiario estagiario = Estagiario.builder().id(dto.getId()).nome(dto.getNome()).ra(dto.getRa())
        .telefone(dto.getTelefone()).email(dto.getEmail()).escolaOrigem(dto.getEscolaOrigem()).faixaRenda(dto.getFaixaRenda())
        .etnia(dto.getEtnia()).genero(dto.getGenero()).curso(dto.getCurso()).semestre(dto.getSemestre()).periodo(dto.getPeriodo())
        .build();
        estagiarioRepository.save(estagiario);
    }

    public EstagiarioDTO converterParaDto (Estagiario estagiario) {
        return EstagiarioDTO.builder().id(estagiario.getId()).nome(estagiario.getNome())
        .ra(estagiario.getRa()).telefone(estagiario.getTelefone()).email(estagiario.getEmail())
		.escolaOrigem(estagiario.getEscolaOrigem()).faixaRenda(estagiario.getFaixaRenda()).etnia(estagiario.getEtnia())
        .genero(estagiario.getGenero()).curso(estagiario.getCurso()).semestre(estagiario.getSemestre()).periodo(estagiario.getPeriodo())
        .build();
    }

    
}
