package br.edu.fatec.estagio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatec.estagio.dto.EmpresaDTO;
import br.edu.fatec.estagio.model.Empresa;
import br.edu.fatec.estagio.repository.EmpresaRepository;

@Service
public class EmpresaService {

    @Autowired 
    private EmpresaRepository empresaRepository;

    public List<Empresa> listarTodas() {
        return empresaRepository.findAll();
    }

    public List<Empresa> buscarPorNome(String nome) {
        return empresaRepository.findByNomeEmpresaContainingIgnoreCase(nome);
    }

    public Optional<Empresa> buscarPorCnpj(String cnpj) {
        return empresaRepository.findByCnpj(cnpj);
    }

    public Optional<Empresa> buscarPorId(Long id) {
        return empresaRepository.findById(id);
    }

    public boolean deletarPorId(Long id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isPresent() && !empresa.get().getContratos().isEmpty()) {
            return false;
        }
        empresaRepository.deleteById(id);
        return true;
    }

    public String validarCnpjDuplicado(EmpresaDTO dto) {
        Optional<Empresa> empresaExiste = empresaRepository.findByCnpj(dto.getCnpj());
        if (empresaExiste.isPresent()) {
            Empresa empresaBanco = empresaExiste.get();
            if (dto.getId() == null) {
                return "Essa empresa já possui cadastro com esse CNPJ!";
            }
            if (!empresaBanco.getId().equals(dto.getId())) {
                return "Este CNPJ já pertence a outra empresa cadastrada!";
            }
        }
        return null;
    }

    public void salvarEmpresa (EmpresaDTO dto) {
        Empresa empresa = Empresa.builder().id(dto.getId()).nomeEmpresa(dto.getNomeEmpresa()).cnpj(dto.getCnpj())
        .areaAtuacao(dto.getAreaAtuacao()).cidade(dto.getCidade()).cep(dto.getCep()).email(dto.getEmail()).site(dto.getSite())
        .telefone(dto.getTelefone()).nomeContato(dto.getNomeContato()).cargoContato(dto.getCargoContato())
        .areaDepartamento(dto.getAreaDepartamento()).linkedin(dto.getLinkedin()).build();
        empresaRepository.save(empresa);
    }

    public EmpresaDTO converterParaDto (Empresa empresa) {
        return EmpresaDTO.builder().id(empresa.getId()).nomeEmpresa(empresa.getNomeEmpresa())
        .cnpj(empresa.getCnpj()).areaAtuacao(empresa.getAreaAtuacao()).cidade(empresa.getCidade())
		.cep(empresa.getCep()).email(empresa.getEmail()).site(empresa.getSite()).telefone(empresa.getTelefone())
		.nomeContato(empresa.getNomeContato()).cargoContato(empresa.getCargoContato()).areaDepartamento(empresa.getAreaDepartamento())
        .linkedin(empresa.getLinkedin()).build();
    }

    
}
