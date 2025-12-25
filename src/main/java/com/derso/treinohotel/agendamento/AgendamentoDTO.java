package com.derso.treinohotel.agendamento;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record AgendamentoDTO(
        @NotNull
        UUID quartoId,

        @NotNull
        LocalDate dataCheckin,

        @NotNull
        LocalDate dataCheckout
) {

    public static AgendamentoDTO fromEntity(Agendamento agendamento) {
        return new AgendamentoDTO(agendamento.
            getId(), agendamento.getCheckin().toLocalDate(), agendamento.getCheckout().toLocalDate());
    }

}
