package com.derso.treinohotel.agendamento;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.derso.treinohotel.config.HotelBusinessException;
import com.derso.treinohotel.quarto.Quarto;
import com.derso.treinohotel.quarto.QuartoRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AgendamentoService {

    private final AgendamentoRepository agendamentoRepository;
    private final QuartoRepository quartoRepository;

    public UUID criar(AgendamentoDTO dados) {
        Quarto quarto = quartoRepository.findById(dados.quartoId())
            .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));

        Agendamento novo = new Agendamento(quarto, dados.dataCheckin(), dados.dataCheckout());

        boolean existeConflito = agendamentoRepository
            .existsByQuartoAndCheckinLessThanAndCheckoutGreaterThan(
                    quarto,
                    novo.getCheckout(),
                    novo.getCheckin()
            );

        if (existeConflito) {
            throw new HotelBusinessException("Quarto já está reservado para o período informado");
        }

        return agendamentoRepository.save(novo).getId();
    }

    public void excluir(UUID id) {
        agendamentoRepository.deleteById(id);
    }

    public List<AgendamentoDTO> listarEntreDatas(UUID quartoId, LocalDate dataInicial, LocalDate dataFinal) {
        if (dataFinal.isBefore(dataInicial)) {
            throw new HotelBusinessException("Data final deve ser igual ou após a data inicial");
        }

        Quarto quarto = quartoRepository.findById(quartoId)
            .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));

        LocalDateTime inicio = dataInicial.atStartOfDay();
        LocalDateTime fim = dataFinal.plusDays(1).atStartOfDay();

        return agendamentoRepository
            .findByQuartoAndCheckinLessThanAndCheckoutGreaterThanOrderByCheckinAsc(quarto, fim, inicio)
            .stream()
            .map(AgendamentoDTO::fromEntity)
            .collect(Collectors.toList());
    }

    public boolean quartoEstaOcupado(UUID quartoId, LocalDate dataInicio, LocalDate dataFim) {
        if (!dataFim.isAfter(dataInicio)) {
            throw new HotelBusinessException("Data final deve ser após a inicial");
        }

        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.plusDays(1).atStartOfDay();

        return agendamentoRepository.existeSobreposicao(quartoId, inicio, fim);
    }
    
}


