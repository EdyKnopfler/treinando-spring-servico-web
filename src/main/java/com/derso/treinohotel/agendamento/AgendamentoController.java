package com.derso.treinohotel.agendamento;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/agendamentos")
@RequiredArgsConstructor
public class AgendamentoController {

    private final AgendamentoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENDADOR')")
    public ResponseEntity<Void> criar(@Valid @RequestBody AgendamentoDTO dto) {
        UUID id = service.criar(dto);
        return ResponseEntity.created(URI.create("/agendamentos/" + id)).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> excluir(@PathVariable UUID id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/ocupado")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENDADOR')")
    public boolean quartoEstaOcupado(
        @RequestParam UUID quartoId,
        @RequestParam LocalDate inicio,
        @RequestParam LocalDate fim
    ) {
        return service.quartoEstaOcupado(quartoId, inicio, fim);
    }

    @GetMapping("/periodo")
    @PreAuthorize("hasRole('ADMIN') or hasRole('AGENDADOR')")
    public List<AgendamentoDTO> listar(
        @RequestParam UUID quartoId,
        @RequestParam LocalDate inicio,
        @RequestParam LocalDate fim
    ) {
        return service.listarEntreDatas(quartoId, inicio, fim);
    }
}
