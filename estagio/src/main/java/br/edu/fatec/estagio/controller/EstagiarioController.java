package br.edu.fatec.estagio.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import br.edu.fatec.estagio.dto.EstagiarioDTO;
import br.edu.fatec.estagio.model.Estagiario;
import br.edu.fatec.estagio.repository.EstagiarioRepository;
import br.edu.fatec.estagio.service.EstagiarioService;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/estagiario")
public class EstagiarioController {
	
	@Autowired
	private EstagiarioService estagiarioService;
	
	@Autowired 
	private EstagiarioRepository estagiarioRepository;
	@GetMapping("/cadastrar")
	public String form(Model model) {
		model.addAttribute("estagiarioDTO", new EstagiarioDTO());
		model.addAttribute("modoEdicao", false);
		return "estagiario/form";
	}

	@PostMapping("/cadastrar")
	public String salvar(@Valid @ModelAttribute ("estagiarioDTO") EstagiarioDTO dto, BindingResult result, Model model){
		String erroDuplicidade = estagiarioService.validarRaDuplicado(dto);
		if (erroDuplicidade != null) {
			model.addAttribute("erroRa", erroDuplicidade);
			model.addAttribute("modoEdicao", dto.getId() != null);
			model.addAttribute("estagiarioDTO", dto);
			return "estagiario/form";
		}
		if (result.hasErrors()) {
			model.addAttribute("modoEdicao", dto.getId() != null);
			return "estagiario/form";
		}
		estagiarioService.salvarEstagiario(dto);
		return "redirect:/estagiario/listar";
	}

	@GetMapping("/listar")
	public String listar(@RequestParam(required = false) String ra, @RequestParam(required = false) String nome, Model model) {
		List<Estagiario> estagiarios;
		if (ra != null && ra.trim().isEmpty()) {
        	ra = null;
		}
		if (nome != null && nome.trim().isEmpty()) {
			nome = null;
		}
		if (ra != null){
			Optional<Estagiario> estagiarioBuscado = estagiarioRepository.findByRa(ra);
			estagiarios = estagiarioBuscado.map(List::of).orElse(List.of());
			model.addAttribute("paramRa", ra);
		} else if (nome != null){
			estagiarios = estagiarioService.buscarPorNome(nome);
			model.addAttribute("paramNome", nome);
		} else {
			estagiarios = estagiarioService.listarTodos();
		}
		model.addAttribute("estagiarios", estagiarios);
		return "estagiario/listagem";
	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable Long id) {
		estagiarioService.deletarPorId(id);
		return "redirect:/estagiario/listar";
	}

	@GetMapping("/editar/{ra}")
    public String editar(@PathVariable String ra, Model model) {
        Optional<Estagiario> estagiarioOptional = estagiarioService.buscarPorRa(ra);
        if (estagiarioOptional.isPresent()) {
			EstagiarioDTO dto = estagiarioService.converterParaDto(estagiarioOptional.get());
			model.addAttribute("estagiarioDTO", dto);
			model.addAttribute("modoEdicao", true);
			return "estagiario/form";
		}
        return "redirect:/estagiario/listar";
    }
	
}
