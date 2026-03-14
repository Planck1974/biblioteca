package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class UtilizadorController {

    @Autowired
    private UsuarioService usuarioService;

    // Listagem de utilizadores
    @GetMapping("/utilizadores")
    public String listar(Model model) {
        try {
            // Verifique se no Service o nome é listarTodos() ou findAll()
            model.addAttribute("utilizadores", usuarioService.listarTodos());
            model.addAttribute("roles", Usuario.Role.values());
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao carregar lista.");
        }
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
            // ATENÇÃO: Se o build falhar aqui, mude 'salvar' para 'save' ou 'registrar'
            usuarioService.salvar(usuario); 
            attrs.addFlashAttribute("sucesso", "Cadastro ok!");
            return "redirect:/auth/login";
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", "Erro: " + e.getMessage());
            return "redirect:/registrar";
        }
    }

    @PostMapping("/utilizadores/role/{id}")
    public String alterarRole(@PathVariable Long id, @RequestParam Usuario.Role role, RedirectAttributes attrs) {
        usuarioService.alterarRole(id, role);
        return "redirect:/utilizadores";
    }

    @GetMapping("/utilizadores/toggle/{id}")
    public String toggleActivo(@PathVariable Long id, RedirectAttributes attrs) {
        usuarioService.activarDesactivar(id);
        return "redirect:/utilizadores";
    }

    @GetMapping("/utilizadores/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes attrs) {
        usuarioService.eliminar(id);
        return "redirect:/utilizadores";
    }
}