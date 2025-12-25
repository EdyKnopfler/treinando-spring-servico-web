package com.derso.treinohotel.quarto;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class QuartoService {

    private final QuartoRepository repository;

    public QuartoService(QuartoRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public QuartoDTO criar(QuartoDTO dados) {
        if (repository.existsByNumero(dados.numero())) {
            throw new NumeroQuartoDuplicadoException(dados.numero());
        }

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

    @Transactional(readOnly = true)
    public QuartoDTO buscarPorNumero(int numero) {
        return repository.findByNumero(numero)
                .map(QuartoDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Quarto " + numero + " não encontrado"));
    }

    @Transactional
    public QuartoDTO atualizar(UUID id, QuartoDTO dados) {
        Optional<Quarto> quartoNoNumero = repository.findByNumero(dados.numero());
        
        quartoNoNumero.ifPresent(quarto -> {
            if (!quarto.getId().equals(id)) {
                throw new NumeroQuartoDuplicadoException(dados.numero());
            }
        });
        
        Quarto quarto = quartoNoNumero.orElseThrow(() -> new EntityNotFoundException("Quarto não encontrado"));
        dados.applyTo(quarto);
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
