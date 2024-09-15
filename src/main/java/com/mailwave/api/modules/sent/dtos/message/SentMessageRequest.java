package com.mailwave.api.modules.sent.dtos.message;

public record SentMessageRequest(
        Long accountId,
        String subject,
        String body,
        Long folderId
) {}
