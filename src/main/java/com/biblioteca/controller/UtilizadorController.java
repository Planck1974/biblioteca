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

    // Listagem (Acesso: /utilizadores)
    @GetMapping("/utilizadores")
    public String listar(Model model) {
        model.addAttribute("utilizadores", usuarioService.listarTodos());
        model.addAttribute("roles", Usuario.Role.values());
        return "utilizadores/lista";
    }

    // --- MÉTODOS DE REGISTRO (Acesso: /registrar) ---
    @GetMapping("/registrar")
    public String mostrarPaginaRegistro(Model model) {
        model.addAttribute("usuario", new Usuario()); 
        return "registrar"; 
    }

    @PostMapping("/registrar")
    public String realizarRegistro(@ModelAttribute("usuario") Usuario usuario, RedirectAttributes attrs) {
        try {
            // Usando o service para garantir que a senha seja tratada corretamente
            usuarioService.salvar(usuario); 
            attrs.addFlashAttribute("sucesso", "Cadastro realizado! Faça login.");
            return "redirect:/login";
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro ao cadastrar: " + e.getMessage());
            return "redirect:/registrar";
        }
    }

    // --- OUTROS MÉTODOS (Tirei o /utilizadores do Mapping e coloquei no link) ---

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