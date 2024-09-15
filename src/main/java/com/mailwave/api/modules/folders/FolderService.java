package com.mailwave.api.modules.folders;

import com.mailwave.api.exceptions.*;
import com.mailwave.api.modules.accounts.AccountRepository;
import com.mailwave.api.modules.folders.dtos.FolderCreateRequest;
import com.mailwave.api.modules.folders.dtos.FolderResponse;
import com.mailwave.api.modules.folders.dtos.FolderUpdateRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class FolderService {

    private final FolderRepository folderRepository;
    private final AccountRepository accountRepository;

    public FolderService(FolderRepository folderRepository, AccountRepository accountRepository) {
        this.folderRepository = folderRepository;
        this.accountRepository = accountRepository;
    }

    public FolderResponse createFolder(FolderCreateRequest request) {
        var account = accountRepository.findById(request.accountId()).orElseThrow(() -> new AccountNotFoundException(request.accountId()));

        try {
            var folder = new Folder(
                    null,
                    request.folderName(),
                    LocalDateTime.now(),
                    account
            );

            var savedFolder = folderRepository.save(folder);

            return new FolderResponse(savedFolder);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }

    }

    public List<FolderResponse> getAllFolders() {
        List<FolderResponse> folders = new ArrayList<>();

        try{
            var results = folderRepository.findAll();

            if (results.isEmpty()) { throw new NoRecordsFoundException(); }

            results.forEach(result -> folders.add(new FolderResponse(result)));

            return folders;

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }

    }


    public FolderResponse getFolderById(Long id) {
        var folder =  folderRepository.findById(id).orElseThrow(() -> new FolderNotFoundException(id));
        return new FolderResponse(folder);
    }


    public FolderResponse updateFolder(FolderUpdateRequest request) {
        var folder = folderRepository.findById(request.id()).orElseThrow(() -> new FolderNotFoundException(request.id()));
        var account = accountRepository.findById(request.accountId()).orElseThrow(() -> new AccountNotFoundException(request.accountId()));

        try{
            folder.setFolderName(request.folderName());
            folder.setAccount(account);

            var updatedFolder = folderRepository.save(folder);

            return new FolderResponse(updatedFolder);

        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }


    public void deleteFolder(Long id) {
        var folder = folderRepository.findById(id).orElseThrow(() -> new FolderNotFoundException(id));

        try{
            folderRepository.delete(folder);

        }catch (DataIntegrityViolationException ex){
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }

    }
}
