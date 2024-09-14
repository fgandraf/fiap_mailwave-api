package com.mailwave.api.exceptions;

public class NoRecordsFoundException extends RuntimeException {
    public NoRecordsFoundException(String message) {
        super(message);
    }

    public NoRecordsFoundException() {
        super("Nenhum registro encontrado.");
    }
}