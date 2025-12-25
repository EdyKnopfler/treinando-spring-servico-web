package com.derso.treinohotel.quarto;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/quartos")
public class QuartoController {

    private final QuartoService service;

    public QuartoController(QuartoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public QuartoDTO criar(@RequestBody @Valid QuartoDTO request) {
        return service.criar(request);
    }

    @GetMapping
    public List<QuartoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public QuartoDTO buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public QuartoDTO atualizar(
            @PathVariable UUID id,
            @RequestBody @Valid QuartoDTO request
    ) {
        return service.atualizar(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar(@PathVariable UUID id) {
        service.deletar(id);
    }

}
