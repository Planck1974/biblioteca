package com.biblioteca.service;

import com.biblioteca.model.Usuario;
import com.biblioteca.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // ─── REGISTRO DE USUÁRIOS ───
    public Usuario registar(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Este email já está registado!");
        }
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        if (usuario.getRole() == null) {
            usuario.setRole(Usuario.Role.ESTUDANTE);
        }
        return usuarioRepository.save(usuario);
    }

    // ─── LISTAR USUÁRIOS ───
    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    // ─── BUSCAR USUÁRIO POR ID ───
    public Usuario buscarPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));
    }

    // ─── BUSCAR USUÁRIO POR EMAIL ───
    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Utilizador não encontrado"));
    }

    // ─── ALTERAR ROLE ───
    public void alterarRole(Long id, Usuario.Role role) {
        Usuario usuario = buscarPorId(id);
        usuario.setRole(role);
        usuarioRepository.save(usuario);
    }

    // ─── ATIVAR / DESATIVAR USUÁRIO ───
    public void activarDesactivar(Long id) {
        Usuario usuario = buscarPorId(id);
        usuario.setActivo(!usuario.isActivo());
        usuarioRepository.save(usuario);
    }

    // ─── ELIMINAR USUÁRIO ───
    public void eliminar(Long id) {
        usuarioRepository.deleteById(id);
    }

    // ─── CONTAR TOTAL DE USUÁRIOS ───
    public long contarTotal() {
        return usuarioRepository.count();
    }

    // ─── CRIAR ADMIN PADRÃO ───
    public void criarAdminPadrao() {
        if (!usuarioRepository.existsByEmail("admin@biblioteca.com")) {
            Usuario admin = new Usuario();
            admin.setNome("Administrador");
            admin.setEmail("admin@biblioteca.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole(Usuario.Role.ADMIN);
            usuarioRepository.save(admin);
            System.out.println("✅ Admin padrão criado: admin@biblioteca.com / admin123");
        }
    }

    // ─── LOGIN ───
    public Usuario login(String email, String senha) {
        try {
            Usuario usuario = buscarPorEmail(email); // pega usuário pelo email
            if (passwordEncoder.matches(senha, usuario.getPassword())) {
                return usuario; // login OK
            } else {
                return null; // senha incorreta
            }
        } catch (RuntimeException e) {
            return null; // usuário não encontrado
        }
    }
}