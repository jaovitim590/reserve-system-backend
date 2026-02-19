package com.reservSystem.ReservSystem.exceptions;

public class RoleInvalidaException extends RuntimeException {
    public RoleInvalidaException() {
        super("Role inválida");
    }
}