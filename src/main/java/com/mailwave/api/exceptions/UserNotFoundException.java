package com.mailwave.api.exceptions;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(Long id) {
        super("Usuário com Id \"" + id + "\" não encontrado");
    }

    public UserNotFoundException() {}

}