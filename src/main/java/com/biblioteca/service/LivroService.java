package com.biblioteca.service;

import com.biblioteca.model.Livro;
import com.biblioteca.repository.LivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * SERVICE — Camada de lógica de negócio para Livros
 * O Controller chama o Service, o Service chama o Repository
 */
@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public List<Livro> listarTodos() {
        return livroRepository.findAll();
    }

    public List<Livro> listarDisponiveis() {
        return livroRepository.findByDisponivel(true);
    }

    public List<Livro> pesquisarPorTitulo(String titulo) {
        return livroRepository.findByTituloContainingIgnoreCase(titulo);
    }

    public Livro buscarPorId(Long id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livro não encontrado com ID: " + id));
    }

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public void eliminar(Long id) {
        livroRepository.deleteById(id);
    }

    public long contarTotal() {
        return livroRepository.count();
    }

    public long contarDisponiveis() {
        return livroRepository.findByDisponivel(true).size();
    }
}
