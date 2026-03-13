package com.biblioteca.controller;

import com.biblioteca.model.Usuario;
import com.biblioteca.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * CONTROLLER — Gere Login, Logout e Registo de novos utilizadores
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    // GET /auth/login — Página de login
    @GetMapping("/login")
    public String loginPage(@RequestParam(required = false) String erro,
                            @RequestParam(required = false) String logout,
                            Model model) {
        if (erro != null) model.addAttribute("erro", "Email ou password incorrectos.");
        if (logout != null) model.addAttribute("logout", "Sessão terminada com sucesso.");
        return "auth/login";
    }

    // GET /auth/registar — Página de registo
    @GetMapping("/registar")
    public String registarPage(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "auth/registar";
    }

    // POST /auth/registar — Processar registo
    @PostMapping("/registar")
    public String registar(@Valid @ModelAttribute Usuario usuario,
                           BindingResult result,
                           RedirectAttributes attrs,
                           Model model) {
        if (result.hasErrors()) {
            return "auth/registar";
        }
        try {
            // Novos registos são sempre ESTUDANTE por defeito
            usuario.setRole(Usuario.Role.ESTUDANTE);
            usuarioService.registar(usuario);
            attrs.addFlashAttribute("sucesso", "Conta criada com sucesso! Faça login.");
            return "redirect:/auth/login";
        } catch (Exception e) {
            model.addAttribute("erro", e.getMessage());
            return "auth/registar";
        }
    }
}
