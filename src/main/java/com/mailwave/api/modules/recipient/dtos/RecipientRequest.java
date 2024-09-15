package com.mailwave.api.modules.recipient.dtos;

public record RecipientRequest(
        String recipientEmail,
        String type,
        Long messageId
) {
    private static Long sentMessageId;

    public Long getSentMessageId() {
        return sentMessageId;
    }
}
