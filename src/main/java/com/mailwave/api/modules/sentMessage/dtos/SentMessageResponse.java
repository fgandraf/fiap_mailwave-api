package com.mailwave.api.modules.sentMessage.dtos;

import com.mailwave.api.modules.sentMessage.models.SentAttachment;
import com.mailwave.api.modules.sentMessage.models.SentMessage;
import java.time.LocalDateTime;
import java.util.List;

public record SentMessageResponse(
        Long id,
        String subject,
        String body,
        LocalDateTime sentAt,
        Long accountId,
        List<SentAttachment> attachments
) {

    public SentMessageResponse(SentMessage sentMessage){
        this(
                sentMessage.getId(),
                sentMessage.getSubject(),
                sentMessage.getBody(),
                sentMessage.getSentAt(),
                sentMessage.getAccount().getId(),
                sentMessage.getAttachments()
        );
    }
}