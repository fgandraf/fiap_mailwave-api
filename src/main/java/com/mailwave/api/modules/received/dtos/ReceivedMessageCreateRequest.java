package com.mailwave.api.modules.received.dtos;

import com.mailwave.api.modules.received.models.ReceivedAttachment;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

public record ReceivedMessageCreateRequest(

        @NotNull(message = "Sender is required.")
        @Size(min = 1, max = 255, message = "Sender must be between 1 and 255 characters.")
        String sender,

        @NotNull(message = "Subject is required.")
        @Size(min = 1, max = 255, message = "Subject must be between 1 and 255 characters.")
        String subject,

        @NotNull(message = "Body is required.")
        String body,

        @NotNull(message = "Account Id is required.")
        Long accountId,

        List<ReceivedAttachment> attachments
) {
}