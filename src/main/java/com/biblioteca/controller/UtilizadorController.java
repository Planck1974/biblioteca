package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UtilizadorController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/utilizadores")
    public String listar(Model model) {
        // CONFIRA: No seu Service é 'listarTodos' ou 'findAll'?
        model.addAttribute("utilizadores", usuarioService.listarTodos());
        model.addAttribute("roles", Usuario.Role.values());
        return "utilizadores/lista";
    }

    @GetMapping("/registrar")
    public String mostrarPaginaRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registrar"; 
    }

    @PostMapping("/registrar")
    public String realizarRegistro(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes attrs) {
        try {
            // CONFIRA: No seu Service é 'salvar' ou 'save'? 
            // Se o build falhou, mude 'salvar' para 'save' aqui embaixo:
            usuarioService.save(usuario); 
            
            attrs.addFlashAttribute("sucesso", "Conta criada!");
            return "redirect:/auth/login";
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro: " + e.getMessage());
            return "redirect:/registrar";
        }
    }
}