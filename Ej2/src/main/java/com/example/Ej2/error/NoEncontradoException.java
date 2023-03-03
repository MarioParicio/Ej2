package com.example.Ej2.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoEncontradoException extends RuntimeException {
    public NoEncontradoException(Long id) {
        super("No se ha encontrado el recurso con id: " + id);
    }

    public NoEncontradoException(int ranking) {
        super("No se ha encontrado el recurso con ranking: " + ranking);
    }
}
