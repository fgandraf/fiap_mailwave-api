package com.mailwave.api.modules.receivedMessage.dtos;

import java.time.LocalDateTime;

public record ReceivedMessageIndexResponse(
        Long id,
        String sender,
        String subject,
        Boolean isRead,
        LocalDateTime receivedAt,
        Long accountId,
        Long folderId
) {
}