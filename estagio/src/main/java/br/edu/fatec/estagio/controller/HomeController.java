package br.edu.fatec.estagio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import br.edu.fatec.estagio.repository.ContratoRepository;
import br.edu.fatec.estagio.repository.EmpresaRepository;
import br.edu.fatec.estagio.repository.EstagiarioRepository;

@Controller
public class HomeController {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Autowired
    private EstagiarioRepository estagiarioRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    // Página inicial: http://localhost:8080/
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("totalEmpresas", empresaRepository.count());
        model.addAttribute("totalEstagiarios", estagiarioRepository.count());
        model.addAttribute("totalContratos", contratoRepository.count());
        return "index";
    }
}
