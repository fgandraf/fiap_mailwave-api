package com.mailwave.api.modules.sent.controllers;

import com.mailwave.api.modules.sent.dtos.message.SentMessageRequest;
import com.mailwave.api.modules.sent.dtos.message.SentMessageResponse;
import com.mailwave.api.modules.sent.services.SentMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sent/messages")
public class SentMessageController {
    private final SentMessageService sentMessageService;

    public SentMessageController(SentMessageService sentMessageService) {
        this.sentMessageService = sentMessageService;
    }

    @PostMapping
    public ResponseEntity<SentMessageResponse> sendMessage(@RequestBody SentMessageRequest request) {
        var message = sentMessageService.sendMessage(request);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SentMessageResponse> getMessageById(@PathVariable Long id) {
        var message = sentMessageService.getSentMessageById(id);
        return ResponseEntity.ok(new SentMessageResponse(message.getId(), message.getSubject(), message.getBody(), message.getSentAt()));
    }
}
