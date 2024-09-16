package com.mailwave.api.modules.received.dtos;

import com.mailwave.api.modules.received.models.ReceivedMessage;

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

    public ReceivedMessageIndexResponse(ReceivedMessage receivedMessage){
        this(
                receivedMessage.getId(),
                receivedMessage.getSender(),
                receivedMessage.getSubject(),
                receivedMessage.getIsRead(),
                receivedMessage.getReceivedAt(),
                receivedMessage.getAccount().getId(),
                receivedMessage.getFolder().getId()
        );
    }
}