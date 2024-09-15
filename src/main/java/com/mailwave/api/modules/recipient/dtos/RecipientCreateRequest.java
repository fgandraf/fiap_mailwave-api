package com.mailwave.api.modules.recipient.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record RecipientCreateRequest(

        @NotNull(message = "Email address is required.")
        @Email(message = "Invalid email address")
        @Size(min = 1, max = 255, message = "Email address must be between 1 and 255 characters.")
        String recipientEmail,

        @NotNull(message = "Type is required")
        @Size(min = 1, max = 10, message = "Type must be between 1 and 10 characters.")
        String type,

        @NotNull(message = "Message Id is required.")
        Long messageId
) { }
