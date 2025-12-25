package com.derso.treinohotel.quarto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record QuartoDTO(
    UUID id,

    @NotNull
    Integer numero,

    @NotNull
    @Positive
    BigDecimal valorDiaria,

    @Size(max = 200)
    String descricao
) {

    public Quarto toEntity() {
        Quarto quarto = new Quarto();
        quarto.setNumero(this.numero);
        quarto.setValorDiaria(this.valorDiaria);
        quarto.setDescricao(this.descricao);
        return quarto;
    }

    public static QuartoDTO fromEntity(Quarto quarto) {
        return new QuartoDTO(
            quarto.getId(),
            quarto.getNumero(),
            quarto.getValorDiaria(),
            quarto.getDescricao()
        );
    }

    public void applyTo(Quarto quarto) {
        quarto.setNumero(this.numero);
        quarto.setValorDiaria(this.valorDiaria);
        quarto.setDescricao(this.descricao);
    }

}