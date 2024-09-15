package com.mailwave.api.modules.sent.dtos.message;

import java.time.LocalDateTime;

public record SentMessageResponse(
        Long id,
        String subject,
        String body,
        LocalDateTime sentAt
) {}
