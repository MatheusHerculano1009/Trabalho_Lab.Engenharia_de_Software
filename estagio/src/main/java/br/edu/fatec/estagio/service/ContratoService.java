package br.edu.fatec.estagio.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.edu.fatec.estagio.dto.ContratoDTO;
import br.edu.fatec.estagio.model.Contrato;
import br.edu.fatec.estagio.model.Empresa;
import br.edu.fatec.estagio.model.Estagiario;
import br.edu.fatec.estagio.repository.ContratoRepository;
import br.edu.fatec.estagio.repository.EmpresaRepository;
import br.edu.fatec.estagio.repository.EstagiarioRepository;

@Service
public class ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EstagiarioRepository estagiarioRepository;

    public List<Contrato> filtrar(String estagiario, String empresa) {
        return contratoRepository
                .findByEstagiarioNomeContainingIgnoreCaseAndEmpresaNomeEmpresaContainingIgnoreCaseOrderByInicioContratoDesc(
                        normalizar(estagiario), normalizar(empresa));
    }

    public Optional<Contrato> buscarPorId(Long id) {
        return contratoRepository.findById(id);
    }

    public void deletarPorId(Long id) {
        contratoRepository.deleteById(id);
    }

    // Retorna a mensagem de erro se o término for anterior ao início; retorna null se estiver correto.
    public String validarDatas(ContratoDTO dto) {
        if (dto.getInicioContrato() != null && dto.getTerminoContrato() != null
                && dto.getTerminoContrato().isBefore(dto.getInicioContrato())) {
            return "A data de término não pode ser anterior à data de início!";
        }
        return null;
    }

    public void salvarContrato(ContratoDTO dto) {
        Contrato contrato;
        if (dto.getId() != null) {
            contrato = contratoRepository.findById(dto.getId()).orElse(new Contrato());
        } else {
            contrato = new Contrato();
        }

        Empresa empresa = empresaRepository.findById(dto.getEmpresaId())
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada."));
        Estagiario estagiario = estagiarioRepository.findById(dto.getEstagiarioId())
                .orElseThrow(() -> new IllegalArgumentException("Estagiário não encontrado."));

        contrato.setEmpresa(empresa);
        contrato.setEstagiario(estagiario);
        contrato.setInicioContrato(dto.getInicioContrato());
        contrato.setTerminoContrato(dto.getTerminoContrato());
        contrato.setCargaHoraria(dto.getCargaHoraria());
        contrato.setArea(dto.getArea().trim());
        contrato.setCargo(dto.getCargo().trim());
        contrato.setFuncao(dto.getFuncao().trim());
        contrato.setModalidade(dto.getModalidade());
        contrato.setSupervisor(vazioParaNull(dto.getSupervisor()));
        contrato.setCargoSupervisor(vazioParaNull(dto.getCargoSupervisor()));

        contratoRepository.save(contrato);
    }

    public ContratoDTO converterParaDto(Contrato contrato) {
        return ContratoDTO.builder().id(contrato.getId())
                .estagiarioId(contrato.getEstagiario().getId())
                .empresaId(contrato.getEmpresa().getId())
                .inicioContrato(contrato.getInicioContrato())
                .terminoContrato(contrato.getTerminoContrato())
                .cargaHoraria(contrato.getCargaHoraria())
                .area(contrato.getArea()).cargo(contrato.getCargo()).funcao(contrato.getFuncao())
                .modalidade(contrato.getModalidade()).supervisor(contrato.getSupervisor())
                .cargoSupervisor(contrato.getCargoSupervisor())
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
