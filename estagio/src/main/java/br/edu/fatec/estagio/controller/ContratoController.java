package br.edu.fatec.estagio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fatec.estagio.dto.ContratoDTO;
import br.edu.fatec.estagio.model.Contrato;
import br.edu.fatec.estagio.service.ContratoService;
import br.edu.fatec.estagio.service.EmpresaService;
import br.edu.fatec.estagio.service.EstagiarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/contrato")
public class ContratoController {

    // Opções dos <select> do formulário. Para mudar uma opção, basta alterar aqui.
    private static final List<String> MODALIDADES = List.of("Presencial", "Remoto", "Híbrido");
    private static final List<String> TIPOS_CONTRATO = List.of("CLT", "PJ");

    @Autowired
    private ContratoService contratoService;

    @Autowired
    private EmpresaService empresaService;

    @Autowired
    private EstagiarioService estagiarioService;

    // Carrega as listas usadas pelos <select> (só quando o formulário é exibido).
    private void carregarOpcoes(Model model) {
        model.addAttribute("modalidades", MODALIDADES);
        model.addAttribute("tiposContrato", TIPOS_CONTRATO);
        model.addAttribute("empresas", empresaService.listarTodas());
        model.addAttribute("estagiarios", estagiarioService.listarTodos());
    }

    @GetMapping("/cadastrar")
    public String form(Model model) {
        model.addAttribute("contratoDTO", new ContratoDTO());
        model.addAttribute("modoEdicao", false);
        carregarOpcoes(model);
        return "contrato/form";
    }

    @PostMapping("/cadastrar")
    public String salvar(@Valid @ModelAttribute("contratoDTO") ContratoDTO dto,
                         BindingResult result, Model model, RedirectAttributes redirect) {
        boolean edicao = dto.getId() != null;

        String erroData = contratoService.validarDatas(dto);
        if (erroData != null) {
            result.rejectValue("terminoContrato", "termino.invalido", erroData);
        }

        if (result.hasErrors()) {
            model.addAttribute("modoEdicao", edicao);
            carregarOpcoes(model);
            return "contrato/form";
        }

        contratoService.salvarContrato(dto);

        if (edicao) {
            redirect.addFlashAttribute("sucesso", "Contrato atualizado com sucesso.");
        } else {
            redirect.addFlashAttribute("sucesso", "Contrato cadastrado com sucesso.");
        }
        return "redirect:/contrato/listar";
    }

    @GetMapping("/listar")
    public String listar(@RequestParam(required = false) String estagiario,
                         @RequestParam(required = false) String empresa, Model model) {
        List<Contrato> contratos = contratoService.filtrar(estagiario, empresa);
        model.addAttribute("contratos", contratos);
        model.addAttribute("paramEstagiario", estagiario);
        model.addAttribute("paramEmpresa", empresa);
        return "contrato/listagem";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        Optional<Contrato> contratoOptional = contratoService.buscarPorId(id);
        if (contratoOptional.isEmpty()) {
            redirect.addFlashAttribute("erro", "Contrato não encontrado.");
            return "redirect:/contrato/listar";
        }
        model.addAttribute("contratoDTO", contratoService.converterParaDto(contratoOptional.get()));
        model.addAttribute("modoEdicao", true);
        carregarOpcoes(model);
        return "contrato/form";
    }

    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirect) {
        contratoService.deletarPorId(id);
        redirect.addFlashAttribute("sucesso", "Contrato excluído com sucesso.");
        return "redirect:/contrato/listar";
    }

}
