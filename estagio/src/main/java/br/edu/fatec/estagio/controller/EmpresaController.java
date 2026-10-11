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
import jakarta.validation.Valid;

@Controller
@RequestMapping("/empresa")
public class EmpresaController {
	
	@Autowired 
	private EmpresaRepository empresaRepository;
	@GetMapping("/cadastrar")
	public String form(Model model) {
		model.addAttribute("empresaDTO", new EmpresaDTO());
		model.addAttribute("modoEdicao", false);
		return "empresa/form";
	}

	@PostMapping("/cadastrar")
	public String salvar(@Valid @ModelAttribute ("empresaDTO") EmpresaDTO dto, BindingResult result, Model model){
		Optional<Empresa> empresaExiste = empresaRepository.findByCnpj(dto.getCnpj());
		if (empresaExiste.isPresent()) {
			Empresa empresaBanco = empresaExiste.get();
			if (dto.getId() == null) {
				model.addAttribute("erroCnpj", "Essa empresa já possui cadastro com esse CNPJ");
				model.addAttribute("modoEdicao", false);
				return "empresa/form";
			}
			if (!empresaBanco.getId().equals(dto.getId())) {
				model.addAttribute("erroCnpj", "Este CNPJ já pertence a outra empresa cadastrada!");
				model.addAttribute("modoEdicao", true);
				return "empresa/form";
			}
		}
		if (result.hasErrors()) {
			model.addAttribute("modoEdicao", dto.getId() != null);
			return "empresa/form";
		}
		Empresa empresa = Empresa.builder().id(dto.getId()).nomeEmpresa(dto.getNomeEmpresa()).cnpj(dto.getCnpj())
        .areaAtuacao(dto.getAreaAtuacao()).cidade(dto.getCidade()).cep(dto.getCep()).email(dto.getEmail()).site(dto.getSite())
        .telefone(dto.getTelefone()).nomeContato(dto.getNomeContato()).cargoContato(dto.getCargoContato())
        .areaDepartamento(dto.getAreaDepartamento()).linkedin(dto.getLinkedin()).build();
		empresaRepository.save(empresa);
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
			empresas = empresaRepository.findByNomeEmpresaContainingIgnoreCase(nome);
			model.addAttribute("paramNome", nome);
		} else {
			empresas = empresaRepository.findAll();
		}
		model.addAttribute("empresas", empresas);
		return "empresa/listagem";
	}

	@GetMapping("/deletar/{id}")
	public String deletar(@PathVariable Long id) {
		empresaRepository.deleteById(id);
		return "redirect:/empresa/listar";
	}

	@GetMapping("/editar/{cnpj}")
    public String editar(@PathVariable String cnpj, Model model) {
        Optional<Empresa> empresaOptional = empresaRepository.findByCnpj(cnpj);
        if (empresaOptional.isPresent()) {
			Empresa empresa = empresaOptional.get();
			EmpresaDTO dto = EmpresaDTO.builder().id(empresa.getId()).nomeEmpresa(empresa.getNomeEmpresa())
            .cnpj(empresa.getCnpj()).areaAtuacao(empresa.getAreaAtuacao()).cidade(empresa.getCidade())
			.cep(empresa.getCep()).email(empresa.getEmail()).site(empresa.getSite()).telefone(empresa.getTelefone())
			.nomeContato(empresa.getNomeContato()).cargoContato(empresa.getCargoContato()).areaDepartamento(empresa.getAreaDepartamento())
            .linkedin(empresa.getLinkedin()).build();
            model.addAttribute("empresaDTO", dto);
			model.addAttribute("modoEdicao", true);
            return "empresa/form";
        }
        return "redirect:/empresa/listar";
    }
	
}
