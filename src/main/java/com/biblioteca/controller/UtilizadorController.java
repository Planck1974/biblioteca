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

    // Listagem - Usando listarTodos() do seu Service
    @GetMapping("/utilizadores")
    public String listar(Model model) {
        model.addAttribute("utilizadores", usuarioService.listarTodos());
        model.addAttribute("roles", Usuario.Role.values());
        return "utilizadores/lista";
    }

    // Exibir página de registro
    @GetMapping("/registrar")
    public String mostrarPaginaRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registrar"; 
    }

    // Processar o formulário de registro
    @PostMapping("/registrar")
    public String realizarRegistro(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes attrs) {
        try {
            // CORREÇÃO AQUI: Mudado de 'save' para 'registar' (igual ao seu Service)
            usuarioService.registar(usuario); 
            
            attrs.addFlashAttribute("sucesso", "Conta criada com sucesso! Faça login.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro: " + e.getMessage());
            return "redirect:/registrar";
        }
    }

    @PostMapping("/utilizadores/role/{id}")
    public String alterarRole(@PathVariable Long id, @RequestParam Usuario.Role role) {
        usuarioService.alterarRole(id, role);
        return "redirect:/utilizadores";
    }

    @GetMapping("/utilizadores/toggle/{id}")
    public String toggleActivo(@PathVariable Long id) {
        usuarioService.activarDesactivar(id);
        return "redirect:/utilizadores";
    }

    @GetMapping("/utilizadores/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);
        return "redirect:/utilizadores";
    }
}