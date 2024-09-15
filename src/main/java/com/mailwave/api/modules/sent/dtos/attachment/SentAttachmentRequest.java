package com.mailwave.api.modules.sent.dtos.attachment;

public record SentAttachmentRequest(
        Long messageId,
        String fileName,
        String fileType,
        byte[] fileData
) {}
