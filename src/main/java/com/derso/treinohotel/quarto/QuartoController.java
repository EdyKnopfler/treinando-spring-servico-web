package com.derso.treinohotel.quarto;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/quartos")
@RequiredArgsConstructor
public class QuartoController {

    private final QuartoService service;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public QuartoDTO criar(@RequestBody @Valid QuartoDTO request) {
        return service.criar(request);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<QuartoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuartoDTO buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/numero/{numero}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuartoDTO buscarPorNumero(@PathVariable int numero) {
        return service.buscarPorNumero(numero);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public QuartoDTO atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid QuartoDTO request
    ) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }

}
