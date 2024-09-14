package com.mailwave.api.modules.folders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FolderService {

    @Autowired
    private FolderRepository folderRepository;

    // Criar uma nova pasta
    public Folder createFolder(Folder folder) {
        folder.setCreatedAt(LocalDateTime.now());
        return folderRepository.save(folder);
    }

    // Buscar todas as pastas
    public List<Folder> getAllFolders() {
        return folderRepository.findAll();
    }

    // Buscar uma pasta por ID
    public Optional<Folder> getFolderById(Long id) {
        return folderRepository.findById(id);
    }

    // Atualizar uma pasta existente
    public Optional<Folder> updateFolder(Long id, Folder folderDetails) {
        return folderRepository.findById(id).map(folder -> {
            folder.setFolderName(folderDetails.getFolderName());
            folder.setCreatedAt(folderDetails.getCreatedAt());
            return folderRepository.save(folder);
        });
    }

    // Deletar uma pasta
    public boolean deleteFolder(Long id) {
        return folderRepository.findById(id).map(folder -> {
            folderRepository.delete(folder);
            return true;
        }).orElse(false);
    }
}
