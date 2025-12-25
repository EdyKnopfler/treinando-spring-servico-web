package com.derso.treinohotel.quarto;

public class NumeroQuartoDuplicadoException extends RuntimeException {
    public NumeroQuartoDuplicadoException(int numero) {
        super("Já existe um quarto com número " + numero);
    }
}

