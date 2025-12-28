package com.derso.treinohotel.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.derso.treinohotel.config.HotelBusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDTO criar(UserDTO dadosUsuario) {
        if (!userRepository.findByEmail(dadosUsuario.email()).isEmpty()) {
            throw new HotelBusinessException("E-mail já usado");
        }

        return UserDTO.fromEntity(
            userRepository.save(
                new User(
                    UUID.randomUUID(), 
                    dadosUsuario.email(), 
                    passwordEncoder.encode(dadosUsuario.password()), 
                    dadosUsuario.userType()
                )
            )
        );
    }

    public Optional<UserDTO> validar(String email, String password) {
        return userRepository.findByEmail(email)
            .filter(user -> passwordEncoder.matches(password, user.getPassword()))
            .map(UserDTO::fromEntity);
    }

    public Optional<UserDTO> porEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTO::fromEntity);
    }

    public void remover(UUID id) {
        userRepository.deleteById(id);
    }

}
