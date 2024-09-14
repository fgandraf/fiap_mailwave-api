package com.mailwave.api.modules.received.services;

import com.mailwave.api.modules.received.models.ReceivedAttachment;
import com.mailwave.api.modules.received.repositories.ReceivedAttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceivedAttachmentService {

    private final ReceivedAttachmentRepository receivedAttachmentRepository;

    @Autowired
    public ReceivedAttachmentService(ReceivedAttachmentRepository receivedAttachmentRepository) {
        this.receivedAttachmentRepository = receivedAttachmentRepository;
    }

    public ReceivedAttachment saveAttachment(ReceivedAttachment attachment) {
        return receivedAttachmentRepository.save(attachment);
    }

    public Optional<ReceivedAttachment> getAttachmentById(Long id) {
        return receivedAttachmentRepository.findById(id);
    }

    public Optional<ReceivedAttachment> updateAttachment(Long id, ReceivedAttachment attachmentDetails) {
        if (receivedAttachmentRepository.existsById(id)) {
            attachmentDetails.setId(id);
            ReceivedAttachment updatedAttachment = receivedAttachmentRepository.save(attachmentDetails);
            return Optional.of(updatedAttachment);
        }
        return Optional.empty();
    }
    public boolean deleteAttachment(Long id) {
        if (receivedAttachmentRepository.existsById(id)) {
            receivedAttachmentRepository.deleteById(id);
            return true;
        }
        return false;
    }


    public List<ReceivedAttachment> getAllAttachments() {
        return receivedAttachmentRepository.findAll();
    }


}
