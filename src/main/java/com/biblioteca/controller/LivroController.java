package com.biblioteca.controller;

import com.biblioteca.model.Livro;
import com.biblioteca.service.LivroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * CONTROLLER — Gere todos os pedidos HTTP relacionados com Livros
 * Recebe pedido → chama Service → devolve View
 */
@Controller
@RequestMapping("/livros")
public class LivroController {

    @Autowired
    private LivroService livroService;

    // GET /livros — Listar todos os livros
    @GetMapping
    public String listar(Model model, @RequestParam(required = false) String pesquisa) {
        if (pesquisa != null && !pesquisa.isEmpty()) {
            model.addAttribute("livros", livroService.pesquisarPorTitulo(pesquisa));
            model.addAttribute("pesquisa", pesquisa);
        } else {
            model.addAttribute("livros", livroService.listarTodos());
        }
        return "livros/lista"; // → templates/livros/lista.html
    }

    // GET /livros/novo — Mostrar formulário de cadastro
    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("livro", new Livro());
        return "livros/formulario"; // → templates/livros/formulario.html
    }

    // POST /livros/salvar — Guardar novo livro
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Livro livro,
                         BindingResult result,
                         RedirectAttributes attrs) {
        if (result.hasErrors()) {
            return "livros/formulario";
        }
        livroService.salvar(livro);
        attrs.addFlashAttribute("sucesso", "Livro cadastrado com sucesso!");
        return "redirect:/livros";
    }

    // GET /livros/editar/{id} — Mostrar formulário de edição
    @GetMapping("/editar/{id}")
    public String editarFormulario(@PathVariable Long id, Model model) {
        model.addAttribute("livro", livroService.buscarPorId(id));
        return "livros/formulario";
    }

    // GET /livros/eliminar/{id} — Eliminar livro
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes attrs) {
        livroService.eliminar(id);
        attrs.addFlashAttribute("sucesso", "Livro eliminado com sucesso!");
        return "redirect:/livros";
    }
}
