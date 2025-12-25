package com.derso.treinohotel.quarto;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<QuartoDTO> criar(@RequestBody @Valid QuartoDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(request));
    }

    @GetMapping
    public List<QuartoDTO> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public QuartoDTO buscar(@PathVariable UUID id) {
        return service.buscarPorId(id);
    }

    @GetMapping("/numero/{numero}")
    public QuartoDTO buscarPorNumero(@PathVariable int numero) {
        return service.buscarPorNumero(numero);
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
