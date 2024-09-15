package com.mailwave.api.modules._received.controllers;

import com.mailwave.api.modules._received.models.ReceivedAttachment;
import com.mailwave.api.modules._received.services.ReceivedAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/received/attachments")
public class ReceivedAttachmentController {

    private final ReceivedAttachmentService receivedAttachmentService;

    @Autowired
    public ReceivedAttachmentController(ReceivedAttachmentService receivedAttachmentService) {
        this.receivedAttachmentService = receivedAttachmentService;
    }

    // Criar um novo anexo recebido
    @PostMapping
    public ResponseEntity<ReceivedAttachment> createReceivedAttachment(@RequestBody ReceivedAttachment attachment) {
        ReceivedAttachment createdAttachment = receivedAttachmentService.saveAttachment(attachment);
        return new ResponseEntity<>(createdAttachment, HttpStatus.CREATED);
    }

    // Buscar todos os anexos recebidos
    @GetMapping
    public ResponseEntity<List<ReceivedAttachment>> getAllReceivedAttachments() {
        List<ReceivedAttachment> attachments = receivedAttachmentService.getAllAttachments();
        return new ResponseEntity<>(attachments, HttpStatus.OK);
    }

    // Buscar um anexo recebido pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ReceivedAttachment> getReceivedAttachmentById(@PathVariable Long id) {
        Optional<ReceivedAttachment> attachment = receivedAttachmentService.getAttachmentById(id);
        return attachment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar um anexo recebido existente
    @PutMapping("/{id}")
    public ResponseEntity<ReceivedAttachment> updateReceivedAttachment(@PathVariable Long id, @RequestBody ReceivedAttachment attachmentDetails) {
        Optional<ReceivedAttachment> updatedAttachment = receivedAttachmentService.updateAttachment(id, attachmentDetails);
        return updatedAttachment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar um anexo recebido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceivedAttachment(@PathVariable Long id) {
        boolean isDeleted = receivedAttachmentService.deleteAttachment(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

