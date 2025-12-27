package com.derso.treinohotel.autenticacao;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.derso.treinohotel.user.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest dadosLogin) {

        // TODO gerar refresh token e criar o endpoint para usá-lo

        return userService.validar(dadosLogin.email(), dadosLogin.password())
            .map(user -> ResponseEntity.ok(
                new LoginResponse(jwtService.generateToken(user), null)))
            .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
    
}
