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

    public Optional<UserDTO> createUser(String email, String password, String userType) {
        if (!userRepository.findByEmail(email).isEmpty()) {
            throw new HotelBusinessException("E-mail já usado");
        }

        return Optional.of(
            userRepository.save(
                new User(UUID.randomUUID(), email, passwordEncoder.encode(password), userType)))
            .map(UserDTO::fromEntity);
    }

    public boolean validateUser(String email, String password) {
        return userRepository.findByEmail(email)
            .map(user -> passwordEncoder.matches(password, user.getPassword()))
            .orElse(false);
    }

    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email).map(UserDTO::fromEntity);
    }

}
