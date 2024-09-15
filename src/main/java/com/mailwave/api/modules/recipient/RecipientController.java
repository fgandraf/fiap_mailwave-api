package com.mailwave.api.modules.recipient;

import com.mailwave.api.modules.recipient.dtos.RecipientCreateRequest;
import com.mailwave.api.modules.recipient.dtos.RecipientResponse;
import com.mailwave.api.modules.recipient.dtos.RecipientUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
    public ResponseEntity<RecipientResponse> createRecipient(@Valid @RequestBody RecipientCreateRequest request) {
        var createRecipient = recipientService.createRecipient(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createRecipient.id()).toUri();
        return ResponseEntity.created(location).body(createRecipient);
    }

    @GetMapping("/message/{messageId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<RecipientResponse>> getAllRecipientsBySentMessageId(@PathVariable Long messageId) {
        var response = recipientService.getAllRecipientsBySentMessageId(messageId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RecipientResponse> getRecipientById(@PathVariable Long id) {
        var response = recipientService.getRecipientById(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<RecipientResponse> updateRecipient(@Valid @RequestBody RecipientUpdateRequest request) {
        var response = recipientService.updateRecipient(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteRecipient(@PathVariable Long id) {
        recipientService.deleteRecipient(id);
        return ResponseEntity.noContent().build();
    }
}
