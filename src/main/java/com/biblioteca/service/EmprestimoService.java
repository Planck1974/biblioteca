package com.biblioteca.service;
 
import com.biblioteca.model.Emprestimo;
import com.biblioteca.model.Livro;
import com.biblioteca.model.Usuario;
import com.biblioteca.repository.EmprestimoRepository;
import com.biblioteca.repository.LivroRepository;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
 
@Service
public class EmprestimoService {
 
    @Autowired
    private EmprestimoRepository emprestimoRepository;
 
    @Autowired
    private LivroRepository livroRepository;
 
    @Autowired
    private UsuarioRepository usuarioRepository;
 
    public Emprestimo emprestarLivro(Long livroId, Long usuarioId) {
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado"));
 
        if (!livro.isDisponivel()) {
            throw new RuntimeException("Livro não está disponível para empréstimo");
        }
 
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));
 
        // Verificar limite de empréstimos por role
        long emprestimosActivos = emprestimoRepository.countByUsuarioIdAndStatus(
                usuarioId, Emprestimo.StatusEmprestimo.ACTIVO);
 
        int maxEmprestimos = usuario.getRole().getMaxEmprestimos();
        if (emprestimosActivos >= maxEmprestimos) {
            throw new RuntimeException("Limite de empréstimos atingido! " +
                    usuario.getRole().name() + " pode ter no máximo " + maxEmprestimos + " empréstimo(s) activo(s).");
        }
 
        // Prazo automático por role
        int prazoDias = usuario.getRole().getPrazoDias();
 
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.setLivro(livro);
        emprestimo.setUsuario(usuario);
        emprestimo.setDataEmprestimo(LocalDate.now());
        emprestimo.setDataDevolucaoPrevista(LocalDate.now().plusDays(prazoDias));
        emprestimo.setStatus(Emprestimo.StatusEmprestimo.ACTIVO);
 
        livro.setDisponivel(false);
        livroRepository.save(livro);
 
        return emprestimoRepository.save(emprestimo);
    }
 
    public Emprestimo devolverLivro(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoRepository.findById(emprestimoId)
                .orElseThrow(() -> new RuntimeException("Empréstimo não encontrado"));
 
        emprestimo.setDataDevolucaoReal(LocalDate.now());
        emprestimo.setStatus(Emprestimo.StatusEmprestimo.DEVOLVIDO);
 
        Livro livro = emprestimo.getLivro();
        livro.setDisponivel(true);
        livroRepository.save(livro);
 
        return emprestimoRepository.save(emprestimo);
    }
 
    public List<Emprestimo> listarTodos() {
        return emprestimoRepository.findAll();
    }
 
    public List<Emprestimo> listarActivos() {
        return emprestimoRepository.findByStatus(Emprestimo.StatusEmprestimo.ACTIVO);
    }
 
    public long contarActivos() {
        return listarActivos().size();
    }
}