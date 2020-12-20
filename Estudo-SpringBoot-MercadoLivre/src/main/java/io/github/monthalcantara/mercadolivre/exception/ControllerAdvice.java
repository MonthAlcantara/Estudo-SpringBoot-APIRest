package io.github.monthalcantara.mercadolivre.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(ApiErrorException.class)
    public ResponseEntity<ErroPadronizado> handleErroPadronizado(ApiErrorException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(new ErroPadronizado(e.getReason()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroPadronizado> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        Collection<String> mensagens = new ArrayList<>();
        e.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> {
                    String message = String.format("Campo %s %s", fieldError.getField(), fieldError.getDefaultMessage());
                    mensagens.add(message);
                });
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroPadronizado(mensagens));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErroPadronizado> handleApiErrorException(ConstraintViolationException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErroPadronizado(Arrays.asList(e.getMessage())));
    }

    }
