package com.derso.treinohotel.quarto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(
    name = "quarto",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_quarto_numero", columnNames = "numero")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Quarto {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private int numero;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal valorDiaria;

    @Column(length = 200)
    private String descricao;
    
}
