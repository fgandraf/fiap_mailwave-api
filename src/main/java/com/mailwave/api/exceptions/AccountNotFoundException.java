package com.mailwave.api.exceptions;

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(Long id) {
        super("Conta com Id \"" + id + "\" não encontrada");
    }

}