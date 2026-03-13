package com.biblioteca.repository;

import com.biblioteca.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * REPOSITORY — Spring Data JPA cria todos os métodos automaticamente
 * Não precisas de escrever SQL!
 */
@Repository
public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByDisponivel(boolean disponivel);
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByAutorContainingIgnoreCase(String autor);
    List<Livro> findByCategoria(String categoria);
}
