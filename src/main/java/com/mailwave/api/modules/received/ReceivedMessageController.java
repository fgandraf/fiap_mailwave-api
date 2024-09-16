package com.mailwave.api.modules.received;

import com.mailwave.api.modules.received.dtos.ReceivedMessageCreateRequest;
import com.mailwave.api.modules.received.dtos.ReceivedMessageIndexResponse;
import com.mailwave.api.modules.received.dtos.ReceivedMessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/received/messages")
public class ReceivedMessageController {

    private final ReceivedMessageService receivedMessageService;

    public ReceivedMessageController(ReceivedMessageService receivedMessageService) {
        this.receivedMessageService = receivedMessageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ReceivedMessageResponse> createReceivedMessage(@Valid @RequestBody ReceivedMessageCreateRequest request) {
        var response = receivedMessageService.createReceivedMessage(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("{messageId}/to-folder/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReceivedMessageResponse> moveToFolder(@PathVariable Long messageId, @PathVariable Long folderId){
        var response = receivedMessageService.moveMessageToFolder(messageId, folderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{messageId}/add-tag/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReceivedMessageResponse> addTag(@PathVariable Long messageId, @PathVariable Long tagId){
        var response = receivedMessageService.addTagToReceivedMessage(messageId, tagId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("folder/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReceivedMessageIndexResponse>> getIndexByFolder(@PathVariable Long folderId){
        var response = receivedMessageService.getAllIndexByFolder(folderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("tag/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReceivedMessageIndexResponse>> getIndexByTag(@PathVariable Long tagId){
        var response = receivedMessageService.getAllIndexFromTag(tagId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/unread/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReceivedMessageIndexResponse>> getUnread(@PathVariable Long accountId){
        var response = receivedMessageService.getAllIndexUnread(accountId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<ReceivedMessageIndexResponse>> getAll(@PathVariable Long accountId){
        var response = receivedMessageService.getAllIndex(accountId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ReceivedMessageResponse> getReceivedMessageById(@PathVariable Long id) {
        var response = receivedMessageService.getReceivedMessageById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteReceivedMessage(@PathVariable Long id) {
        receivedMessageService.deleteReceivedMessage(id);
        return ResponseEntity.noContent().build();
    }

}