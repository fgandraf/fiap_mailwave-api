package com.mailwave.api.modules._received.controllers;

import com.mailwave.api.modules._received.models.ReceivedMessage;
import com.mailwave.api.modules._received.services.ReceivedMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/received/messages")
public class ReceivedMessageController {

    private final ReceivedMessageService receivedMessageService;

    @Autowired
    public ReceivedMessageController(ReceivedMessageService receivedMessageService) {
        this.receivedMessageService = receivedMessageService;
    }

    // Criar uma nova mensagem recebida
    @PostMapping
    public ResponseEntity<ReceivedMessage> createReceivedMessage(@RequestBody ReceivedMessage message) {
        ReceivedMessage createdMessage = receivedMessageService.saveMessage(message);
        return new ResponseEntity<>(createdMessage, HttpStatus.CREATED);
    }

    // Buscar todas as mensagens recebidas
    @GetMapping
    public ResponseEntity<List<ReceivedMessage>> getAllReceivedMessages() {
        List<ReceivedMessage> messages = receivedMessageService.getAllMessages();
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    // Buscar uma mensagem recebida pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<ReceivedMessage> getReceivedMessageById(@PathVariable Long id) {
        Optional<ReceivedMessage> message = receivedMessageService.getMessageById(id);
        return message.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar uma mensagem recebida existente
    @PutMapping("/{id}")
    public ResponseEntity<ReceivedMessage> updateReceivedMessage(@PathVariable Long id, @RequestBody ReceivedMessage messageDetails) {
        Optional<ReceivedMessage> updatedMessage = receivedMessageService.updateMessage(id, messageDetails);
        return updatedMessage.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar uma mensagem recebida
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceivedMessage(@PathVariable Long id) {
        boolean isDeleted = receivedMessageService.deleteMessage(id);
        return isDeleted ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
