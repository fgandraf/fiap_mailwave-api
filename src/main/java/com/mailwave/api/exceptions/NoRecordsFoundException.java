package com.mailwave.api.exceptions;

public class NoRecordsFoundException extends RuntimeException {

    public NoRecordsFoundException() {
        super("Nenhum registro encontrado.");
    }
}