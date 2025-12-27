package com.derso.treinohotel.config;

import java.util.UUID;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.derso.treinohotel.user.UserDTO;
import com.derso.treinohotel.user.UserRepository;
import com.derso.treinohotel.user.UserService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminBootstrap implements ApplicationRunner {

    private static final String DEFAULT_EMAIL = "admin@admin.com";
    private static final String DEFAULT_PASSWORD = "admin";

    private final UserRepository userRepository;
    private final UserService userService;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        if (userRepository.count() == 0) {
            UserDTO dadosUsuario = new UserDTO(UUID.randomUUID(), DEFAULT_EMAIL, DEFAULT_PASSWORD, "ADMIN");
            userService.criar(dadosUsuario);
            System.out.println("TABELA DE USUÁRIOS VAZIA!");
            System.out.println("Criado usuário " + dadosUsuario.email() + ", senha: " + dadosUsuario.password());
            System.out.println("==> Use-o para criar seu primeiro usuário de produção e APAGUE-O!");
        }
    }
    
}
