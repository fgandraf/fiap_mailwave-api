package com.mailwave.api.exceptions;

public class ReceivedMessageNotFoundException extends RuntimeException {
    public ReceivedMessageNotFoundException(Long id) {
        super("Mensagem recebida com ID " + id + " não foi encontrada.");
    }
}
