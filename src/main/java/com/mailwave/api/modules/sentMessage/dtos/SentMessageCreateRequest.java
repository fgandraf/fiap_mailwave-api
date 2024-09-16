package com.mailwave.api.modules.sentMessage.dtos;

import com.mailwave.api.modules.sentMessage.models.SentAttachment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record SentMessageCreateRequest(

        @NotNull(message = "Subject is required.")
        @Size(min = 1, max = 255, message = "Subject must be between 1 and 255 characters.")
        String subject,

        @NotNull(message = "Body is required.")
        String body,

        @NotNull(message = "Account Id is required.")
        Long accountId,

        List<SentAttachment> attachments
) {
}