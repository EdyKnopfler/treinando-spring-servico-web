package com.derso.treinohotel.autenticacao;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
    @NotEmpty String email,
    @NotEmpty String password
) {
    
}
