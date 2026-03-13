package com.biblioteca.repository;

import com.biblioteca.model.Utilizador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UtilizadorRepository extends JpaRepository<Utilizador, Long> {
    List<Utilizador> findByNomeContainingIgnoreCase(String nome);
}
