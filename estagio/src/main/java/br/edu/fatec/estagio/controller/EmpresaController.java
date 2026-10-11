package br.edu.fatec.estagio.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import br.edu.fatec.estagio.model.Empresa;
import br.edu.fatec.estagio.repository.EmpresaRepository;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired 
	private EmpresaRepository empresaRepository;
	@GetMapping("/cadastrar")
	public String form(Model model) {
		model.addAttribute("empresa", new Empresa());
		return "empresa/form";
	}

	@PostMapping("/cadastrar")
	public String salvar(Empresa empresa){
		empresaRepository.save(empresa);
		return "redirect:/empresa/cadastrar";
	}

	@GetMapping("/listar")
	public String listar(@RequestParam(required = false) String cnpj, Model model) {
		List<Empresa> empresas;
		if (cnpj != null && cnpj.isEmpty()){
			Optional<Empresa> empresaBuscada = empresaRepository.findByCnpj(cnpj);
			empresas = empresaBuscada.map(List::of).orElse(List.of());
			model.addAttribute("paramCnpj", cnpj);
		} else {
			empresas = empresaRepository.findAll();
		}
		model.addAttribute("empresas", empresas);
		return "empresa/listagem";
	}

	@GetMapping("/deletar/{cnpj}")
    public String deletar(@PathVariable Long cnpj) {
        empresaRepository.deleteById(cnpj);
        return "redirect:/empresa/listar";
    }

	@GetMapping("/editar/{cnpj}")
    public String editar(@PathVariable String cnpj, Model model) {
        Optional<Empresa> empresa = empresaRepository.findByCnpj(cnpj);
        if (empresa.isPresent()) {
            model.addAttribute("empresa", empresa.get());
            return "empresa/form";
        }
        return "redirect:/empresa/listar";
    }
	
}
