package com.mailwave.api.modules.receivedMessage.dtos;

import com.mailwave.api.modules.receivedMessage.models.ReceivedAttachment;
import com.mailwave.api.modules.receivedMessage.models.ReceivedMessage;

import java.time.LocalDateTime;
import java.util.List;

public record ReceivedMessageResponse(
        Long id,
        String sender,
        String subject,
        String body,
        LocalDateTime receivedAt,
        Long accountId,
        List<ReceivedAttachment> attachments
) {

    public ReceivedMessageResponse(ReceivedMessage receivedMessage){
        this(
                receivedMessage.getId(),
                receivedMessage.getSender(),
                receivedMessage.getSubject(),
                receivedMessage.getBody(),
                receivedMessage.getReceivedAt(),
                receivedMessage.getAccount().getId(),
                receivedMessage.getAttachments()
        );
    }
}