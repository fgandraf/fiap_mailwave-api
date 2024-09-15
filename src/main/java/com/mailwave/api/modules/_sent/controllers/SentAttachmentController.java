package com.mailwave.api.modules._sent.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sent/attachments")
public class SentAttachmentController {
//    private final SentAttachmentService sentAttachmentService;
//
//    public SentAttachmentController(SentAttachmentService sentAttachmentService) {
//        this.sentAttachmentService = sentAttachmentService;
//    }
//    @PostMapping
//    public ResponseEntity<SentAttachmentResponse> createAttachment(@RequestBody SentAttachmentRequest request) {
//        var attachment = sentAttachmentService.createAttachment(request);
//        return ResponseEntity.ok(attachment);
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<SentAttachmentResponse> getAttachmentById(@PathVariable Long id) {
//        var attachment = sentAttachmentService.getAttachmentById(id);
//        return ResponseEntity.ok(attachment);
//    }
//
//    @GetMapping
//    public ResponseEntity<List<SentAttachmentResponse>> getAllAttachments() {
//        return ResponseEntity.ok(sentAttachmentService.getAllAttachments());
//    }
}
