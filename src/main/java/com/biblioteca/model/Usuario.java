package com.biblioteca.model;
 
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
 
@Entity
@Table(name = "usuarios")
public class Usuario {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
 
    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    @Column(unique = true, nullable = false)
    private String email;
 
    @NotBlank(message = "Password é obrigatória")
    @Column(nullable = false)
    private String password;
 
    private String telefone;
 
    @Enumerated(EnumType.STRING)
    private Role role = Role.ESTUDANTE;
 
    private boolean activo = true;
 
    public enum Role {
        ADMIN, PROFESSOR, ESTUDANTE, VISITANTE;
 
        public int getMaxEmprestimos() {
            switch (this) {
                case ADMIN: return Integer.MAX_VALUE;
                case PROFESSOR: return 5;
                case ESTUDANTE: return 3;
                case VISITANTE: return 1;
                default: return 1;
            }
        }
 
        public int getPrazoDias() {
            switch (this) {
                case ADMIN: return 365;
                case PROFESSOR: return 15;
                case ESTUDANTE: return 7;
                case VISITANTE: return 1;
                default: return 1;
            }
        }
    }
 
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
 
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
 
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
 
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
 
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
 
    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }
 
    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}