package com.mailwave.api.exceptions;

public class SentMessageNotFoundException extends RuntimeException {
    public SentMessageNotFoundException(Long id) {
        super("Mensagem enviada com ID " + id + " não foi encontrada.");
    }
}
