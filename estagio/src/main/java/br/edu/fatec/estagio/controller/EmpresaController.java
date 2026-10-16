package br.edu.fatec.estagio.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import br.edu.fatec.estagio.dto.EmpresaDTO;
import br.edu.fatec.estagio.model.Empresa;
import br.edu.fatec.estagio.repository.EmpresaRepository;
import br.edu.fatec.estagio.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired
	private EmpresaService empresaService;
	
	@Autowired 
	private EmpresaRepository empresaRepository;
	@GetMapping("/cadastrar")
	public String form(Model model) {
		model.addAttribute("empresaDTO", new EmpresaDTO());
		model.addAttribute("modoEdicao", false);
		return "empresa/form";
	}

	@PostMapping("/cadastrar")
	public String salvar(@Valid @ModelAttribute ("empresaDTO") EmpresaDTO dto, BindingResult result, Model model, RedirectAttributes redirect){
		String erroDuplicidade = empresaService.validarCnpjDuplicado(dto);
		if (erroDuplicidade != null) {
			model.addAttribute("erroCnpj", erroDuplicidade);
			model.addAttribute("modoEdicao", dto.getId() != null);
			model.addAttribute("empresaDTO", dto);
			return "empresa/form";
		}
		if (result.hasErrors()) {
			model.addAttribute("modoEdicao", dto.getId() != null);
			return "empresa/form";
		}
		boolean edicao = dto.getId() != null;
		empresaService.salvarEmpresa(dto);
		if (edicao) {
			redirect.addFlashAttribute("sucesso", "Empresa atualizada com sucesso.");
		} else {
			redirect.addFlashAttribute("sucesso", "Empresa cadastrada com sucesso.");
		}
		return "redirect:/empresa/listar";
	}

	@GetMapping("/listar")
	public String listar(@RequestParam(required = false) String cnpj, @RequestParam(required = false) String nome, Model model) {
		List<Empresa> empresas;
		if (cnpj != null && cnpj.trim().isEmpty()) {
        	cnpj = null;
		}
		if (nome != null && nome.trim().isEmpty()) {
			nome = null;
		}
		if (cnpj != null){
			Optional<Empresa> empresaBuscada = empresaRepository.findByCnpj(cnpj);
			empresas = empresaBuscada.map(List::of).orElse(List.of());
			model.addAttribute("paramCnpj", cnpj);
		} else if (nome != null){
			empresas = empresaService.buscarPorNome(nome);
			model.addAttribute("paramNome", nome);
		} else {
			empresas = empresaService.listarTodas();
		}
		model.addAttribute("empresas", empresas);
		return "empresa/listagem";
	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable Long id) {
		empresaService.deletarPorId(id);
		return "redirect:/empresa/listar";
	}

	@GetMapping("/editar/{cnpj}")
    public String editar(@PathVariable String cnpj, Model model) {
        Optional<Empresa> empresaOptional = empresaService.buscarPorCnpj(cnpj);
        if (empresaOptional.isPresent()) {
			EmpresaDTO dto = empresaService.converterParaDto(empresaOptional.get());
			model.addAttribute("empresaDTO", dto);
			model.addAttribute("modoEdicao", true);
			return "empresa/form";
		}
        return "redirect:/empresa/listar";
    }
	
}
