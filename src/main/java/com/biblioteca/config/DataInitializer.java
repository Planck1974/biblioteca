package com.biblioteca.config;

import com.biblioteca.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * Executado ao iniciar a aplicação
 * Cria o utilizador ADMIN padrão se não existir
 */
@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioService usuarioService;

    @Override
    public void run(String... args) {
        usuarioService.criarAdminPadrao();
    }
}
