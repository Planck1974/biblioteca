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

    // Listagem de utilizadores - Acessível em /utilizadores
    @GetMapping("/utilizadores")
    public String listar(Model model) {
        try {
            model.addAttribute("utilizadores", usuarioService.listarTodos());
            model.addAttribute("roles", Usuario.Role.values());
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar lista: " + e.getMessage());
        }
        return "utilizadores/lista";
    }

    // Exibir página de registro - Acessível em /registrar
    @GetMapping("/registrar")
    public String mostrarPaginaRegistro(Model model) {
        if (!model.containsAttribute("usuario")) {
            model.addAttribute("usuario", new Usuario());
        }
        return "registrar"; 
    }

    // Processar o formulário de registro
    @PostMapping("/registrar")
    public String realizarRegistro(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes attrs) {
        try {
            // Tenta salvar o usuário usando o Service
            usuarioService.salvar(usuario); 
            attrs.addFlashAttribute("sucesso", "Cadastro realizado com sucesso! Faça o login.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            attrs.addFlashAttribute("usuario", usuario);
            return "redirect:/registrar";
        }
    }

    // Outras ações de Admin
    @PostMapping("/utilizadores/role/{id}")
    public String alterarRole(@PathVariable Long id, @RequestParam Usuario.Role role, RedirectAttributes attrs) {
        usuarioService.alterarRole(id, role);
        attrs.addFlashAttribute("sucesso", "Função actualizada!");
        return "redirect:/utilizadores";
    }

    @GetMapping("/utilizadores/toggle/{id}")
    public String toggleActivo(@PathVariable Long id, RedirectAttributes attrs) {
        usuarioService.activarDesactivar(id);
        attrs.addFlashAttribute("sucesso", "Estado actualizado!");
        return "redirect:/utilizadores";
    }

    @GetMapping("/utilizadores/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes attrs) {
        usuarioService.eliminar(id);
        attrs.addFlashAttribute("sucesso", "Utilizador eliminado!");
        return "redirect:/utilizadores";
    }
}