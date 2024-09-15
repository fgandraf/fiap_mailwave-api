package com.mailwave.api.modules.tags;

import com.mailwave.api.modules.tags.dtos.TagCreateRequest;
import com.mailwave.api.modules.tags.dtos.TagResponse;
import com.mailwave.api.modules.tags.dtos.TagUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tags")
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagResponse> createTag(@Valid @RequestBody TagCreateRequest tag) {
        var createdTag = tagService.createTag(tag);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTag.id()).toUri();
        return ResponseEntity.created(location).body(createdTag);
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags(){
        var tags = tagService.getAllTags();
        return ResponseEntity.ok(tags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable("id") Long id){
        var tag = tagService.getTagById(id);
        return ResponseEntity.ok(tag);
    }

    @PutMapping
    public ResponseEntity<TagResponse> updateTag(@Valid @RequestBody TagUpdateRequest tag){
        var updatedTag = tagService.updateTag(tag);
        return ResponseEntity.ok(updatedTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

}
