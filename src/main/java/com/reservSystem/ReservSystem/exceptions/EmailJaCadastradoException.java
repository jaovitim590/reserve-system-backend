package com.reservSystem.ReservSystem.exceptions;

public class EmailJaCadastradoException extends RuntimeException {
    public EmailJaCadastradoException() {
        super("Email já cadastrado");
    }
}