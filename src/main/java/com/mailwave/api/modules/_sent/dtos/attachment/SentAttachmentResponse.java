package com.mailwave.api.modules._sent.dtos.attachment;

public record SentAttachmentResponse(
        Long id,
        String fileName,
        String fileType,
        Long messageId
) {}
