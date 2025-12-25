package com.derso.treinohotel.agendamento;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.derso.treinohotel.quarto.Quarto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Table(name = "agendamentos")
@Getter
public class Agendamento {

    private static final LocalTime HORA_CHECKIN = LocalTime.of(15, 0);
    private static final LocalTime HORA_CHECKOUT = LocalTime.of(12, 0);

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "quarto_id", nullable = false)
    private Quarto quarto;

    @Column(nullable = false)
    private LocalDateTime checkin;

    @Column(nullable = false)
    private LocalDateTime checkout;

    Agendamento() {
        // Para uso do JPA ao recuperar os agendamentos
    }

    public Agendamento(Quarto quarto, LocalDate dataCheckin, LocalDate dataCheckout) {
        if (!dataCheckout.isAfter(dataCheckin)) {
            throw new IllegalArgumentException("Data de checkout deve ser após o check-in");
        }

        this.quarto = quarto;
        this.checkin = dataCheckin.atTime(HORA_CHECKIN);
        this.checkout = dataCheckout.atTime(HORA_CHECKOUT);
    }

    public BigDecimal calcularValorHospedagem() {
        long dias = ChronoUnit.DAYS.between(checkin.toLocalDate(), checkout.toLocalDate());
        return quarto.getValorDiaria().multiply(BigDecimal.valueOf(dias));
    }
}

