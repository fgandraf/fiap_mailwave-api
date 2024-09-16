package com.mailwave.api.modules.sentMessage;

import com.mailwave.api.modules.sentMessage.dtos.SentMessageCreateRequest;
import com.mailwave.api.modules.sentMessage.dtos.SentMessageIndexResponse;
import com.mailwave.api.modules.sentMessage.dtos.SentMessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/sent/messages")
public class SentMessageController {
    

    private final SentMessageService sentMessageService;

    public SentMessageController(SentMessageService SentMessageService) {
        this.sentMessageService = SentMessageService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<SentMessageResponse> createSentMessage(@Valid @RequestBody SentMessageCreateRequest request) {
        var response = sentMessageService.createSentMessage(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("{/id}").buildAndExpand(response.id()).toUri();
        return ResponseEntity.created(location).body(response);
    }

    @PutMapping("{messageId}/to-folder/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SentMessageResponse> moveToFolder(@PathVariable Long messageId, @PathVariable Long folderId){
        var response = sentMessageService.moveMessageToFolder(messageId, folderId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("{messageId}/add-tag/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SentMessageResponse> addTag(@PathVariable Long messageId, @PathVariable Long tagId){
        var response = sentMessageService.addTagToSentMessage(messageId, tagId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("folder/{folderId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SentMessageIndexResponse>> getIndexByFolder(@PathVariable Long folderId){
        var response = sentMessageService.getAllIndexByFolder(folderId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("tag/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SentMessageIndexResponse>> getIndexByTag(@PathVariable Long tagId){
        var response = sentMessageService.getAllIndexFromTag(tagId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<SentMessageIndexResponse>> getAll(@PathVariable Long accountId){
        var response = sentMessageService.getAllIndex(accountId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<SentMessageResponse> getSentMessageById(@PathVariable Long id) {
        var response = sentMessageService.getSentMessageById(id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteSentMessage(@PathVariable Long id) {
        sentMessageService.deleteSentMessage(id);
        return ResponseEntity.noContent().build();
    }
}
