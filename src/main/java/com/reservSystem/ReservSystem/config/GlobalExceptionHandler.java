package com.reservSystem.ReservSystem.config;

import com.reservSystem.ReservSystem.DTOS.ErrorResponse;
import com.reservSystem.ReservSystem.exceptions.EmailJaCadastradoException;
import com.reservSystem.ReservSystem.exceptions.RecursoNaoEncontradoException;
import com.reservSystem.ReservSystem.exceptions.RoleInvalidaException;
import com.reservSystem.ReservSystem.exceptions.StatusInvalidoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailJaCadastradoException.class)
    public ResponseEntity<ErrorResponse> handleEmailJaCadastrado(EmailJaCadastradoException e) {
        return build(HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponse> handleNaoEncontrado(RecursoNaoEncontradoException e) {
        return build(HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDenied(AccessDeniedException e) {
        return build(HttpStatus.FORBIDDEN, "Acesso negado");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> err.getField() + ": " + err.getDefaultMessage())
                .collect(Collectors.joining(", "));
        return build(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneric(Exception e) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Erro interno do servidor");
    }

    private ResponseEntity<ErrorResponse> build(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(new ErrorResponse(status.value(), message, Instant.now()));
    }

    @ExceptionHandler(RoleInvalidaException.class)
    public ResponseEntity<ErrorResponse> handleRoleInvalida(RoleInvalidaException e) {
        return build(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    @ExceptionHandler(StatusInvalidoException.class)
    public ResponseEntity<ErrorResponse> handleStatusInvalido(StatusInvalidoException e){
        return build(HttpStatus.BAD_REQUEST, e.getMessage());
    }
}