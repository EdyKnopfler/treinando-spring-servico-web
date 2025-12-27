package com.derso.treinohotel.quarto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;

import java.util.Optional;
import java.util.UUID;

public interface QuartoRepository extends JpaRepository<Quarto, UUID> {

    Optional<Quarto> findByNumero(int numero);

    boolean existsByNumero(int numero);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select q from Quarto q where q.id = :id")
    Optional<Quarto> findByIdForUpdate(@Param("id") UUID id);
    
}
