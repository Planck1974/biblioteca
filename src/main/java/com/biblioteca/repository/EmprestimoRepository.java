package com.biblioteca.repository;
 
import com.biblioteca.model.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
 
@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Long> {
    List<Emprestimo> findByStatus(Emprestimo.StatusEmprestimo status);
    List<Emprestimo> findByUsuarioId(Long usuarioId);
    List<Emprestimo> findByLivroId(Long livroId);
    long countByUsuarioIdAndStatus(Long usuarioId, Emprestimo.StatusEmprestimo status);
}
 