package com.derso.treinohotel.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    
    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> criar(@RequestBody @Valid UserDTO request) {

        // TODO Quero que somente admins poderiam criar usuário; depois garantimos um script que cria o primeiro admin

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.criar(request));
    }

}
