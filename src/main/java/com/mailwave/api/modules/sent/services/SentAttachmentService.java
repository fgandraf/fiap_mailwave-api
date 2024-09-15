package com.mailwave.api.modules.sent.services;

import com.mailwave.api.modules.sent.dtos.attachment.SentAttachmentRequest;
import com.mailwave.api.modules.sent.dtos.attachment.SentAttachmentResponse;
import com.mailwave.api.modules.sent.models.SentAttachment;
import com.mailwave.api.modules.sent.repositories.SentAttachmentRepository;
import com.mailwave.api.modules.sent.models.SentMessage;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SentAttachmentService {
    private final SentAttachmentRepository sentAttachmentRepository;
    private final SentMessageService sentMessageService;

    public SentAttachmentService(SentAttachmentRepository sentAttachmentRepository, SentMessageService sentMessageService) {
        this.sentAttachmentRepository = sentAttachmentRepository;
        this.sentMessageService = sentMessageService;
    }

    public SentAttachmentResponse createAttachment(SentAttachmentRequest request) {
        SentMessage sentMessage = sentMessageService.getSentMessageById(request.messageId());

        SentAttachment attachment = new SentAttachment();
        attachment.setFileName(request.fileName());
        attachment.setFileType(request.fileType());
        attachment.setFileData(request.fileData());
        attachment.setSentMessage(sentMessage);

        var savedAttachment = sentAttachmentRepository.save(attachment);
        return new SentAttachmentResponse(savedAttachment.getId(), savedAttachment.getFileName(), savedAttachment.getFileType(), savedAttachment.getSentMessage().getId());
    }

    public SentAttachmentResponse getAttachmentById(Long id) {
        var attachment = sentAttachmentRepository.findById(id).orElseThrow(() -> new RuntimeException("Attachment not found"));
        return new SentAttachmentResponse(attachment.getId(), attachment.getFileName(), attachment.getFileType(), attachment.getSentMessage().getId());
    }

    public List<SentAttachmentResponse> getAllAttachments() {
        return sentAttachmentRepository.findAll().stream()
                .map(attachment -> new SentAttachmentResponse(attachment.getId(), attachment.getFileName(), attachment.getFileType(), attachment.getSentMessage().getId()))
                .collect(Collectors.toList());
    }
}
