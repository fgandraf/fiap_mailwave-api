package com.mailwave.api.modules.recipient.dtos;

import com.mailwave.api.modules.recipient.Recipient;

public record RecipientResponse(
        Long id,
        String recipientEmail,
        String type,
        Long messageId
) {
    public RecipientResponse(Recipient recipient) {
        this(
                recipient.getId(),
                recipient.getRecipientEmail(),
                recipient.getType(),
                recipient.getSentMessage().getId()
        );
    }
}
