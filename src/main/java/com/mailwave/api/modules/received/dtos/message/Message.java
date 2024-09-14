package com.mailwave.api.modules.received.dtos.message;

import com.mailwave.api.modules.received.dtos.attachment.Attachment;

import java.time.LocalDateTime;
import java.util.List;

public class Message {

    private Long id;
    private String senderEmail;
    private String subject;
    private String body;
    private LocalDateTime receivedAt;
    private List<Attachment> attachments;

    // Construtor padrão
    public Message() {}

    // Construtor com parâmetros
    public Message(Long id, String senderEmail, String subject, String body, LocalDateTime receivedAt, List<Attachment> attachments) {
        this.id = id;
        this.senderEmail = senderEmail;
        this.subject = subject;
        this.body = body;
        this.receivedAt = receivedAt;
        this.attachments = attachments;
    }

    // Getters e Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }
}
