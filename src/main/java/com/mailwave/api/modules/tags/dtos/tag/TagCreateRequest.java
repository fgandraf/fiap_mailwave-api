package com.mailwave.api.modules.tags.dtos.tag;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TagCreateRequest(

        @NotNull(message = "Tag Name is required.")
        @Size(min = 1, max = 100, message = "Tag Name must be between 1 and 100 characters")
        String tagName,

        @NotNull(message = "Account ID is required.")
        Long accountId
) {
}
