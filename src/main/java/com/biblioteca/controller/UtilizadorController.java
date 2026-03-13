package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * CONTROLLER — Gestão de utilizadores (apenas ADMIN)
 */
@Controller
@RequestMapping("/utilizadores")
public class UtilizadorController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("utilizadores", usuarioService.listarTodos());
        model.addAttribute("roles", Usuario.Role.values());
        return "utilizadores/lista";
    }

    @PostMapping("/role/{id}")
    public String alterarRole(@PathVariable Long id,
                              @RequestParam Usuario.Role role,
                              RedirectAttributes attrs) {
        usuarioService.alterarRole(id, role);
        attrs.addFlashAttribute("sucesso", "Função actualizada!");
        return "redirect:/utilizadores";
    }

    @GetMapping("/toggle/{id}")
    public String toggleActivo(@PathVariable Long id, RedirectAttributes attrs) {
        usuarioService.activarDesactivar(id);
        attrs.addFlashAttribute("sucesso", "Estado actualizado!");
        return "redirect:/utilizadores";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes attrs) {
        usuarioService.eliminar(id);
        attrs.addFlashAttribute("sucesso", "Utilizador eliminado!");
        return "redirect:/utilizadores";
    }
}
