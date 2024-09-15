package com.mailwave.api.exceptions;

public class TagNotFoundException extends RuntimeException{

    public TagNotFoundException(Long id) {
        super("Tag com Id \"" + id + "\" não encontrada");
    }

}