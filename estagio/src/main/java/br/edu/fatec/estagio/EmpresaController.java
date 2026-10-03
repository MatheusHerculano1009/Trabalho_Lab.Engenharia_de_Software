package br.edu.fatec.estagio;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;


@Controller
@RequestMapping("/ator")
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository empresaRepository;
	
	@Autowired
	private EmpresaService empresaService;
	
	@GetMapping ("/formulario")                  
	public String carregaPaginaFormulario (Long id, Model model){ 
		model.addAttribute("empresa", empresaService.getAllEmpresa());
		if(id != null) {
	        var empresa = empresaRepository.getReferenceById(id);
	        model.addAttribute("empresa", empresa);
	    }
	    return "ator/formulario";              
	}   
	
	// Para editar filme com ID
		@GetMapping ("/formulario/{id}")                  
		public String carregaPaginaFormulario (@PathVariable("id") Long id, 
				HttpSession session,
				RedirectAttributes redirectAttributes, 
				Model model){ 

			try {
				Empresa empresa = empresaRepository.findById(id)
						.orElseThrow(() -> new EntityNotFoundException("Empresa não encontrada"));
				model.addAttribute("filmes", estagiarioEmpresa.getAllFilme());
				model.addAttribute("filmesAtor", estagiarioService.buscarFilmesAtor(id));
				model.addAttribute("empresa", empresa);
				return "ator/formulario";

			} catch (EntityNotFoundException e) {
				redirectAttributes.addFlashAttribute("error", e.getMessage());
				return "redirect:/ator";
			}
		}   
	
	@GetMapping                                           
	public String carregaPaginaListagem (Model model){    
		
		//model.addAttributes("filmeAtor", );
	    model.addAttribute("lista", empresaRepository.findAll(Sort.by("nome").ascending()));
	    return "empresa/listagem";                         
	} 

	@PostMapping
	@Transactional
	public String cadastrar ( @Valid DadosCadastroEmpresa dados, @RequestParam("empresasIds") List<Long> empresasIds) {
		Estagiario estagiario = new Estagiario (dados);

		// Busca os Filmes pelos IDs cria uma lista de Filmes 
		List<Estagiario> estagiarios = estagiarioService.findAllById(estagiariosIds);
	
//		for (Filme filme : filmes) {
//			//System.out.println("Filme " + filme.getTitulo());
//		} 
		empresa.setEstagiarios(estagiarios);
		atorRepository.save(ator);
		return   "redirect:ator/formulario";      
	}
	
	@PutMapping
	@Transactional
	public String atualizar (DadosAtualizacaoAtor dados) {
		var ator = atorRepository.getReferenceById(dados.id());
		ator.atualizarInformacoes(dados);
		return "redirect:ator";  
	}
	
	@DeleteMapping
	@Transactional
	public String removeAtor (Long id) {
		atorRepository.deleteById (id);
		return "redirect:ator";  
	}
	
}
