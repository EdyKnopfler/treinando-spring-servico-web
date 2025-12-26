package com.derso.treinohotel.user;

import java.util.UUID;

public record UserDTO(
    UUID id,
    String name,
    String password,
    String userType
) {

    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getEmail(), null, user.getUserType());
    }
    
}
