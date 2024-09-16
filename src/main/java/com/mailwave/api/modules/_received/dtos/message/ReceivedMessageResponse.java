package com.mailwave.api.modules._received.dtos.message;

import com.mailwave.api.modules._received.models.ReceivedMessage;
import com.mailwave.api.modules._sent.models.SentAttachment;

import java.time.LocalDateTime;
import java.util.List;

public record ReceivedMessageResponse(
        String sender,
        String subject,
        String body,
        LocalDateTime receivedAt,
        Long accountId,
        List<SentAttachment> attachments
) {

    public ReceivedMessageResponse(ReceivedMessage receivedMessage){
        this(
                receivedMessage.getSender(),
                receivedMessage.getSubject(),
                receivedMessage.getBody(),
                receivedMessage.getReceivedAt(),
                receivedMessage.getAccount().getId(),
                receivedMessage.getAttachments()
        );
    }
}