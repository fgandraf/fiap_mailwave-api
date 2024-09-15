package com.mailwave.api.modules.tags.controllers;

import com.mailwave.api.modules.tags.dtos.tag.TagCreateRequest;
import com.mailwave.api.modules.tags.dtos.tag.TagResponse;
import com.mailwave.api.modules.tags.dtos.tag.TagUpdateRequest;
import com.mailwave.api.modules.tags.models.Tag;
import com.mailwave.api.modules.tags.services.TagService;
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
    public ResponseEntity<TagResponse> createTag(@RequestBody TagCreateRequest tag) {
        TagResponse createdTag = tagService.createTag(tag);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTag.id()).toUri();
        return ResponseEntity.created(location).body(createdTag);
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> getAllTags(){
        List<TagResponse> allTags = tagService.getAllTags();
        return ResponseEntity.ok(allTags);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponse> getTagById(@PathVariable("id") Long id){
        Tag tag = tagService.getTagById(id);
        return ResponseEntity.ok(new TagResponse(tag));
    }

    @PutMapping
    public ResponseEntity<TagResponse> updateTag(@RequestBody TagUpdateRequest tag){
        TagResponse updatedTag = tagService.updateTag(tag);
        return ResponseEntity.ok(updatedTag);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("id") Long id){
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

}
