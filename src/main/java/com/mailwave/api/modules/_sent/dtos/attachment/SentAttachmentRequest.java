package com.mailwave.api.modules._sent.dtos.attachment;

public record SentAttachmentRequest(
        Long messageId,
        String fileName,
        String fileType,
        byte[] fileData
) {}
