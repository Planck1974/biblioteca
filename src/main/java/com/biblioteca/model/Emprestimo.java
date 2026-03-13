package com.biblioteca.model;
 
import jakarta.persistence.*;
import java.time.LocalDate;
 
@Entity
@Table(name = "emprestimos")
public class Emprestimo {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;
 
    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;
 
    @Column(nullable = false)
    private LocalDate dataEmprestimo;
 
    private LocalDate dataDevolucaoPrevista;
    private LocalDate dataDevolucaoReal;
 
    @Enumerated(EnumType.STRING)
    private StatusEmprestimo status = StatusEmprestimo.ACTIVO;
 
    public enum StatusEmprestimo {
        ACTIVO, DEVOLVIDO, ATRASADO
    }
 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }
 
    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
 
    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }
 
    public LocalDate getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(LocalDate dataDevolucaoPrevista) { this.dataDevolucaoPrevista = dataDevolucaoPrevista; }
 
    public LocalDate getDataDevolucaoReal() { return dataDevolucaoReal; }
    public void setDataDevolucaoReal(LocalDate dataDevolucaoReal) { this.dataDevolucaoReal = dataDevolucaoReal; }
 
    public StatusEmprestimo getStatus() { return status; }
    public void setStatus(StatusEmprestimo status) { this.status = status; }
}