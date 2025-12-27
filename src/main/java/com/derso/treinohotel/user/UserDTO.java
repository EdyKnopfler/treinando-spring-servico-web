package com.derso.treinohotel.user;

import java.util.UUID;

import jakarta.validation.constraints.NotEmpty;

public record UserDTO(
    UUID id,
    @NotEmpty String email,
    @NotEmpty String password,
    @NotEmpty String userType
) {

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getEmail(), null, user.getUserType());
    }
    
}
