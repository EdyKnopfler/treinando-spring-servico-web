package com.derso.treinohotel.quarto;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface QuartoRepository extends JpaRepository<Quarto, UUID> {

    Optional<Quarto> findByNumero(int numero);

    boolean existsByNumero(int numero);
    
}
