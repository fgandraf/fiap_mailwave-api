package com.mailwave.api.modules.recipient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipients")
public class RecipientController {

    @Autowired
    private RecipientService recipientService;

    // Criar um novo destinatário
    @PostMapping
    public ResponseEntity<Recipient> createRecipient(@RequestBody Recipient recipient) {
        Recipient createdRecipient = recipientService.createRecipient(recipient);
        return new ResponseEntity<>(createdRecipient, HttpStatus.CREATED);
    }

    // Buscar todos os destinatários
    @GetMapping
    public ResponseEntity<List<Recipient>> getAllRecipients() {
        List<Recipient> recipients = recipientService.getAllRecipients();
        return new ResponseEntity<>(recipients, HttpStatus.OK);
    }

    // Buscar um destinatário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<Recipient> getRecipientById(@PathVariable Long id) {
        Optional<Recipient> recipient = recipientService.getRecipientById(id);
        return recipient.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Atualizar um destinatário existente
    @PutMapping("/{id}")
    public ResponseEntity<Recipient> updateRecipient(@PathVariable Long id, @RequestBody Recipient recipientDetails) {
        Optional<Recipient> updatedRecipient = recipientService.updateRecipient(id, recipientDetails);
        return updatedRecipient.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Deletar um destinatário
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        boolean isDeleted = recipientService.deleteRecipient(id);
        if (isDeleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
