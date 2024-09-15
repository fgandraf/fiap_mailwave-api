package com.mailwave.api.modules.folders.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record FolderCreateRequest(

        @NotNull(message = "Folder Name is required.")
        @Size(min = 1, max = 100, message = "Folder Name must be between 1 and 100 characters.")
        String folderName,

        @NotNull(message = "Account ID is required.")
        Long accountId
) {
}
