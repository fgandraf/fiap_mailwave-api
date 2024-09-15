package com.mailwave.api.modules.folders.dtos;

import com.mailwave.api.modules.folders.Folder;

import java.time.LocalDateTime;

public record FolderResponse(
        Long id,
        String folderName,
        LocalDateTime createdAt,
        Long accountId
) {
    public FolderResponse(Folder folder){
        this(
            folder.getId(),
            folder.getFolderName(),
            folder.getCreatedAt(),
            folder.getAccount().getId()
        );
    }
}
