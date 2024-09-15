package com.mailwave.api.modules._sent.controllers;

import com.mailwave.api.modules._sent.dtos.message.SentMessageRequest;
import com.mailwave.api.modules._sent.dtos.message.SentMessageResponse;
import com.mailwave.api.modules._sent.services.SentMessageService;
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
    public ResponseEntity<SentMessageResponse> createMessage(@RequestBody SentMessageRequest request) {
        var message = sentMessageService.createSentMessage(request);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SentMessageResponse> getSentMessageById(@PathVariable Long id) {
        var sentMessage = sentMessageService.getSentMessageById(id);
        return ResponseEntity.ok(sentMessage);
    }
}
