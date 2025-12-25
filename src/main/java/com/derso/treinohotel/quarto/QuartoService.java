package com.derso.treinohotel.quarto;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class QuartoService {

    private final QuartoRepository repository;

    public QuartoService(QuartoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuartoDTO criar(QuartoDTO dados) {
        Quarto quarto = dados.toEntity();
        Quarto salvo = repository.save(quarto);
        return QuartoDTO.fromEntity(salvo);
    }

    @Transactional(readOnly = true)
    public List<QuartoDTO> listar() {
        return repository.findAll()
                .stream()
                .map(QuartoDTO::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public QuartoDTO buscarPorId(UUID id) {
        Quarto quarto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
        return QuartoDTO.fromEntity(quarto);
    }

    @Transactional
    public QuartoDTO atualizar(UUID id, QuartoDTO dto) {
        Quarto quarto = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));

        // reaproveita o DTO para atualizar a entidade existente
        dto.applyTo(quarto);

        return QuartoDTO.fromEntity(quarto);
    }

    @Transactional
    public void deletar(UUID id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Quarto não encontrado");
        }
        repository.deleteById(id);
    }
}
