package com.mailwave.api.exceptions;

public class RecipientNotFoundException extends RuntimeException {
    public RecipientNotFoundException(Long id) {
        super("Destinatário com ID " + id + " não foi encontrado.");
    }
}
