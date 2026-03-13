package com.biblioteca.controller;

import com.biblioteca.service.EmprestimoService;
import com.biblioteca.service.LivroService;
import com.biblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private EmprestimoService emprestimoService;

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("totalLivros", livroService.contarTotal());
        model.addAttribute("livrosDisponiveis", livroService.contarDisponiveis());
        model.addAttribute("totalUtilizadores", usuarioService.contarTotal());
        model.addAttribute("emprestimosActivos", emprestimoService.contarActivos());
        return "index";
    }
}
