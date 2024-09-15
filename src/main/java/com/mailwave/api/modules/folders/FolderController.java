package com.mailwave.api.modules.folders;

import com.mailwave.api.modules.folders.dtos.FolderCreateRequest;
import com.mailwave.api.modules.folders.dtos.FolderResponse;
import com.mailwave.api.modules.folders.dtos.FolderUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.util.List;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    private final FolderService folderService;

    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping
    public ResponseEntity<FolderResponse> createFolder(@Valid @RequestBody FolderCreateRequest folder) {
        var createdFolder = folderService.createFolder(folder);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdFolder.id()).toUri();
        return ResponseEntity.created(location).body(createdFolder);
    }

    @GetMapping
    public ResponseEntity<List<FolderResponse>> getAllFolders() {
        var folders = folderService.getAllFolders();
        return ResponseEntity.ok(folders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FolderResponse> getFolderById(@PathVariable Long id) {
        var folderResponse = folderService.getFolderById(id);
        return ResponseEntity.ok(folderResponse);
    }

    @PutMapping
    public ResponseEntity<FolderResponse> updateFolder(@Valid @RequestBody FolderUpdateRequest request) {
        var response = folderService.updateFolder(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long id) {
        folderService.deleteFolder(id);
        return ResponseEntity.noContent().build();
    }

}