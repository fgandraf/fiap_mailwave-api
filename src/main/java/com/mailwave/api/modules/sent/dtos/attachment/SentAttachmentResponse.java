package com.mailwave.api.modules.sent.dtos.attachment;

public record SentAttachmentResponse(
        Long id,
        String fileName,
        String fileType,
        Long messageId
) {}
