package com.mailwave.api.modules.sentMessage.dtos;

import java.time.LocalDateTime;

public record SentMessageIndexResponse(
        Long id,
        String subject,
        LocalDateTime sentAt,
        Long accountId
) {
}