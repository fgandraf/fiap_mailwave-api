package com.mailwave.api.modules.folders;

import com.mailwave.api.modules.folders.dtos.FolderCreateRequest;
import com.mailwave.api.modules.folders.dtos.FolderResponse;
import com.mailwave.api.modules.folders.dtos.FolderUpdateRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/folders")
public class FolderController {

    @Autowired
    private FolderService folderService;

    // Criar uma nova pasta
    @PostMapping
    public ResponseEntity<FolderResponse> createFolder(@RequestBody FolderCreateRequest folder) {
        FolderResponse createdFolder = folderService.createFolder(folder);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdFolder.id()).toUri();
        return ResponseEntity.created(location).body(createdFolder);
    }

    // Buscar todas as pastas
    @GetMapping
    public ResponseEntity<List<FolderResponse>> getAllFolders() {
        List<FolderResponse> folders = folderService.getAllFolders();
        return ResponseEntity.ok(folders);
    }

    // Buscar uma pasta por ID
    @GetMapping("/{id}")
    public ResponseEntity<FolderResponse> getFolderById(@PathVariable Long id) {
        Folder folderResponse = folderService.getFolderById(id);
        return ResponseEntity.ok(new FolderResponse(folderResponse));
    }

    // Atualizar uma pasta existente
    @PutMapping
    public ResponseEntity<FolderResponse> updateFolder(@RequestBody FolderUpdateRequest request) {
        FolderResponse response = folderService.updateFolder(request);
        return ResponseEntity.ok(response);
    }

    // Deletar uma pasta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long id) {
        folderService.deleteFolder(id);
        return ResponseEntity.noContent().build();
    }
}
