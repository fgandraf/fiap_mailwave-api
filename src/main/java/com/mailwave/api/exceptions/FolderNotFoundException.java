package com.mailwave.api.exceptions;

public class FolderNotFoundException extends RuntimeException{

    public FolderNotFoundException(Long id) {
        super("Pasta com Id \"" + id + "\" não encontrada");
    }

}