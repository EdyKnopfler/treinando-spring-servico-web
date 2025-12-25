package com.derso.treinohotel.agendamento;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.derso.treinohotel.quarto.Quarto;

public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {

    boolean existsByQuartoAndCheckinLessThanAndCheckoutGreaterThan(
            Quarto quarto,
            LocalDateTime checkout,
            LocalDateTime checkin
    );

    List<Agendamento> findByQuartoAndCheckinLessThanAndCheckoutGreaterThanOrderByCheckinAsc(
            Quarto quarto,
            LocalDateTime fim,
            LocalDateTime inicio
    );

    @Query("""
        select count(a) > 0
        from Agendamento a
        where a.quarto.id = :quartoId
          and a.checkout > :inicio
          and a.checkin < :fim
    """)
    boolean existeSobreposicao(
        @Param("quartoId") UUID quartoId,
        @Param("inicio") LocalDateTime inicio,
        @Param("fim") LocalDateTime fim
    );
    
}

