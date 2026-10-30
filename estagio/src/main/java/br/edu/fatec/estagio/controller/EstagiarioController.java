package br.edu.fatec.estagio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.edu.fatec.estagio.dto.EstagiarioDTO;
import br.edu.fatec.estagio.model.Estagiario;
import br.edu.fatec.estagio.service.EstagiarioService;
import jakarta.validation.Valid;


@Controller
@RequestMapping("/estagiario")
public class EstagiarioController {

    // Opções dos <select> do formulário. Para mudar uma opção, basta alterar aqui.
    private static final List<String> TURNOS = List.of("Manhã", "Tarde", "Noite");
    private static final List<String> FAIXAS_RENDA = List.of(
        "Até 1 salário Mínimo",
        "De 1 a 3 salários Mínimos",
        "De 3 a 5 salários Mínimos",
        "Acima de 5 salários Mínimos"
    );
    private static final List<String> ETNIAS = List.of("Branca", "Preta", "Parda", "Amarela");
    private static final List<String> GENEROS = List.of("Feminino", "Masculino", "Outro");

    @Autowired
    private EstagiarioService estagiarioService;

  
    @ModelAttribute("turnos")
    public List<String> turnos() {
        return TURNOS;
    }

    @ModelAttribute("faixasRenda")
    public List<String> faixasRenda() {
        return FAIXAS_RENDA;
    }

    @ModelAttribute("etnias")
    public List<String> etnias() {
        return ETNIAS;
    }

    @ModelAttribute("generos")
    public List<String> generos() {
        return GENEROS;
    }

    @GetMapping("/cadastrar")
    public String form(Model model) {
        model.addAttribute("estagiarioDTO", new EstagiarioDTO());
        model.addAttribute("modoEdicao", false);
        return "estagiario/form";
    }

    
    @PostMapping("/cadastrar")
    public String salvar(@Valid @ModelAttribute("estagiarioDTO") EstagiarioDTO dto,
                         BindingResult result, Model model, RedirectAttributes redirect) {
        boolean edicao = dto.getId() != null;

       
        String erroDuplicidade = estagiarioService.validarRaDuplicado(dto);
        if (erroDuplicidade != null) {
            result.rejectValue("ra", "ra.duplicado", erroDuplicidade);
        }

        
        if (result.hasErrors()) {
            model.addAttribute("modoEdicao", edicao);
            return "estagiario/form";
        }

        estagiarioService.salvarEstagiario(dto);

        // Mensagem exibida uma única vez na listagem.
        if (edicao) {
            redirect.addFlashAttribute("sucesso", "Estagiário atualizado com sucesso.");
        } else {
            redirect.addFlashAttribute("sucesso", "Estagiário cadastrado com sucesso.");
        }

    
        return "redirect:/estagiario/listar";
    }


    // Se o RA for informado, ele tem prioridade sobre nome e curso.
    @GetMapping("/listar")
    public String listar(@RequestParam(required = false) String ra,
                         @RequestParam(required = false) String nome,
                         @RequestParam(required = false) String curso, Model model) {
        List<Estagiario> estagiarios = estagiarioService.filtrar(ra, nome, curso);
        model.addAttribute("estagiarios", estagiarios);

        model.addAttribute("paramRa", ra);
        model.addAttribute("paramNome", nome);
        model.addAttribute("paramCurso", curso);
        return "estagiario/listagem";
    }

    // A edição é feita pelo RA, que identifica o aluno e não pode ser alterado.
    @GetMapping("/editar/{ra}")
    public String editar(@PathVariable String ra, Model model, RedirectAttributes redirect) {
        Optional<Estagiario> estagiarioOptional = estagiarioService.buscarPorRa(ra);
        if (estagiarioOptional.isEmpty()) {
            redirect.addFlashAttribute("erro", "Estagiário não encontrado.");
            return "redirect:/estagiario/listar";
        }
        EstagiarioDTO dto = estagiarioService.converterParaDto(estagiarioOptional.get());
        model.addAttribute("estagiarioDTO", dto);
        model.addAttribute("modoEdicao", true);
        return "estagiario/form";
    }

    // Usei POST e não GET só para evitar que um link não apague registros por acidente.
    @PostMapping("/deletar/{id}")
    public String deletar(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        boolean deletado = estagiarioService.deletarPorId(id);
        if (deletado) {
            redirectAttributes.addFlashAttribute("sucesso", "Estagiário excluído com sucesso.");
        } else {
            redirectAttributes.addFlashAttribute("erro", "Não é possível excluir este estagiário pois existem contratos ativos vinculados a ele.");
        }
		estagiarioService.deletarPorId(id);
        return "redirect:/estagiario/listar";
    }

}
