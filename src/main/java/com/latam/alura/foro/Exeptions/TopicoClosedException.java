package com.latam.alura.foro.Exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.FORBIDDEN)
public class TopicoClosedException extends RuntimeException {
    public TopicoClosedException(String message){super(message);}
}
