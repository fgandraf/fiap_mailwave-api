package com.mailwave.api.modules._sent.dtos.message;

import com.mailwave.api.modules._sent.models.SentMessage;

import java.time.LocalDateTime;

public record SentMessageResponse(
        Long id,
        String subject,
        String body,
        LocalDateTime sentAt
) {
    public SentMessageResponse(SentMessage sentMessage){
        this(
                sentMessage.getId(),
                sentMessage.getSubject(),
                sentMessage.getBody(),
                sentMessage.getSentAt()
        );
    }
}
