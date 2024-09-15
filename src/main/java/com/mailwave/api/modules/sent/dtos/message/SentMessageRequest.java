package com.mailwave.api.modules.sent.dtos.message;

import java.time.LocalDateTime;

public record SentMessageRequest(
        Long accountId,
        String subject,
        String body,
        LocalDateTime sentAt,
        Long folderId
) {}
