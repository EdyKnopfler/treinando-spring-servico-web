package com.derso.treinohotel.quarto;

import com.derso.treinohotel.config.HotelBusinessException;

public class NumeroQuartoDuplicadoException extends HotelBusinessException {
    public NumeroQuartoDuplicadoException(int numero) {
        super("Já existe um quarto com número " + numero);
    }
}

