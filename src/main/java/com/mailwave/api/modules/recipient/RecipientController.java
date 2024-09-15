package com.mailwave.api.modules.recipient;

import com.mailwave.api.modules.recipient.dtos.RecipientRequest;
import com.mailwave.api.modules.recipient.dtos.RecipientResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipients")
public class RecipientController {

    private final RecipientService recipientService;
    public RecipientController(RecipientService recipientService) {
        this.recipientService = recipientService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<RecipientResponse> createRecipient(@Valid @RequestBody RecipientRequest request) {
        var response = recipientService.createRecipient(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RecipientResponse>> getAllRecipients() {
        var response = recipientService.getAllRecipients();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RecipientResponse> getRecipientById(@PathVariable Long id) {
        var response = recipientService.getRecipientById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RecipientResponse> updateRecipient(@Valid @RequestBody RecipientRequest request, @PathVariable Long id) {
        var response = recipientService.updateRecipient(request, id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        recipientService.deleteRecipient(id);
        return ResponseEntity.noContent().build();
    }
}
