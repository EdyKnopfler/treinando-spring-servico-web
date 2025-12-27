package com.derso.treinohotel.autenticacao;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.derso.treinohotel.user.UserDTO;
import com.derso.treinohotel.user.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDTO dadosUsuario = userService.porEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("Usuário " + email + " não encontrado"));

        return User.withUsername(email).roles(dadosUsuario.userType()).build();
    }
    
}