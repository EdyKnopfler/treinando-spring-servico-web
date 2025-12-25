package com.derso.treinohotel.agendamento;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.derso.treinohotel.config.HotelBusinessException;
import com.derso.treinohotel.quarto.Quarto;
import com.derso.treinohotel.quarto.QuartoDTO;
import com.derso.treinohotel.quarto.QuartoRepository;

import jakarta.transaction.Transactional;

@SpringBootTest
@Transactional
public class AgendamentoServiceTest {

    
    @Autowired
    private AgendamentoService agendamentoService;
    
    @Autowired
    private QuartoRepository quartoRepository;
    
    private Quarto quarto;
    private AgendamentoDTO natalEAnoNovo, antesDoNatal, aposAnoNovo, conflitanteCom1, conflitanteComVarios;

    @BeforeEach
    void setup() {
        quarto = new QuartoDTO(null, 404, new BigDecimal("100.00"), "Quarto 404").toEntity();
        quartoRepository.save(quarto);
        natalEAnoNovo = new AgendamentoDTO(quarto.getId(), LocalDate.of(2025, 12, 24), LocalDate.of(2026, 1, 2));
        antesDoNatal = new AgendamentoDTO(quarto.getId(), LocalDate.of(2025, 12, 20), LocalDate.of(2025, 12, 24));
        aposAnoNovo = new AgendamentoDTO(quarto.getId(), LocalDate.of(2026, 1, 2), LocalDate.of(2026, 1, 3));
        conflitanteCom1 = new AgendamentoDTO(quarto.getId(), LocalDate.of(2025, 12, 31), LocalDate.of(2026, 1, 2));
        conflitanteComVarios = new AgendamentoDTO(quarto.getId(), LocalDate.of(2025, 12, 22), LocalDate.of(2026, 1, 3));
    }

    @Test
    void deveAceitarPrimeiroAgendamento() {
        assertDoesNotThrow(() -> agendamentoService.criar(natalEAnoNovo));
    }

    @Test
    void deveAceitarCheckinNoDiaDoCheckoutAnterior() {
        agendamentoService.criar(natalEAnoNovo);
        assertDoesNotThrow(() -> agendamentoService.criar(aposAnoNovo));
    }

    @Test
    void deveAceitarAgendamentoQueTerminaNoDiaDoCheckinSeguinte() {
        agendamentoService.criar(natalEAnoNovo);
        assertDoesNotThrow(() -> agendamentoService.criar(antesDoNatal));
    }

    @Test
    void deveFalharQuandoHaSobreposicaoReal() {
        agendamentoService.criar(natalEAnoNovo);
        assertThrows(HotelBusinessException.class, () -> agendamentoService.criar(conflitanteCom1));
    }

    @Test
    void deveFalharQuandoHaSobreposicaoRealComMaisDeUm() {
        agendamentoService.criar(antesDoNatal);
        agendamentoService.criar(natalEAnoNovo);
        agendamentoService.criar(aposAnoNovo);
        assertThrows(HotelBusinessException.class, () -> agendamentoService.criar(conflitanteComVarios));
    }

    @Test
    void listagem() {
        agendamentoService.criar(antesDoNatal);
        agendamentoService.criar(natalEAnoNovo);
        agendamentoService.criar(aposAnoNovo);

        List<AgendamentoDTO> agendamentos = agendamentoService.listarEntreDatas(
            quarto.getId(), LocalDate.of(2025, 12, 1), LocalDate.of(2026, 1, 31));

        Assertions.assertEquals(3, agendamentos.size());
    }

    @Test
    void consultaOcupado() {
        agendamentoService.criar(antesDoNatal);
        agendamentoService.criar(natalEAnoNovo);
        agendamentoService.criar(aposAnoNovo);
        Assertions.assertTrue(agendamentoService.quartoEstaOcupado(quarto.getId(), LocalDate.of(2026, 1, 1), LocalDate.of(2026, 1, 2)));
        Assertions.assertFalse(agendamentoService.quartoEstaOcupado(quarto.getId(), LocalDate.of(2026, 2, 1), LocalDate.of(2026, 2, 28)));
    }

}
