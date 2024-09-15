package com.mailwave.api.modules.recipient.dtos;

public record RecipientResponse(
        Long id,
        String recipientEmail,
        String type,
        Long messageId
) {}
