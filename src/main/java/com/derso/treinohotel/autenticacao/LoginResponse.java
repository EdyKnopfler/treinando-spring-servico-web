package com.derso.treinohotel.autenticacao;

public record LoginResponse(
    String token,
    String refreshToken
) {
    
}
