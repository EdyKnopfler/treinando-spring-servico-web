package com.derso.treinohotel.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.derso.treinohotel.quarto.NumeroQuartoDuplicadoException;

import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class TrataErros {

    static record ErroDTO(boolean error, String message) {}
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErroDTO> erroGeral(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErroDTO(true, e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroDTO> maluco() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErroDTO(true, "Objeto inválido"));
    }

    @ExceptionHandler(NumeroQuartoDuplicadoException.class)
    public ResponseEntity<String> handleNumeroDuplicado(NumeroQuartoDuplicadoException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleNotFound(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

}
