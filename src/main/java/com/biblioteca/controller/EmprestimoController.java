package com.biblioteca.controller;
 
import com.biblioteca.service.EmprestimoService;
import com.biblioteca.service.LivroService;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
@Controller
@RequestMapping("/emprestimos")
public class EmprestimoController {
 
    @Autowired
    private EmprestimoService emprestimoService;
 
    @Autowired
    private LivroService livroService;
 
    @Autowired
    private UsuarioRepository usuarioRepository;
 
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("emprestimos", emprestimoService.listarTodos());
        return "emprestimos/lista";
    }
 
    @GetMapping("/novo")
    public String novoFormulario(Model model) {
        model.addAttribute("livros", livroService.listarDisponiveis());
        model.addAttribute("utilizadores", usuarioRepository.findAll());
        return "emprestimos/formulario";
    }
 
    @PostMapping("/emprestar")
    public String emprestar(@RequestParam Long livroId,
                            @RequestParam Long utilizadorId,
                            RedirectAttributes attrs) {
        try {
            emprestimoService.emprestarLivro(livroId, utilizadorId);
            attrs.addFlashAttribute("sucesso", "Empréstimo registado com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/emprestimos";
    }
 
    @GetMapping("/devolver/{id}")
    public String devolver(@PathVariable Long id, RedirectAttributes attrs) {
        try {
            emprestimoService.devolverLivro(id);
            attrs.addFlashAttribute("sucesso", "Devolução registada com sucesso!");
        } catch (Exception e) {
            attrs.addFlashAttribute("erro", e.getMessage());
        }
        return "redirect:/emprestimos";
    }
}