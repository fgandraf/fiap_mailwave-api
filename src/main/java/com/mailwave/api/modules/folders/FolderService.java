package com.mailwave.api.modules.folders;

import com.mailwave.api.exceptions.DatabaseOperationException;
import com.mailwave.api.exceptions.FolderNotFoundException;
import com.mailwave.api.exceptions.NoRecordsFoundException;
import com.mailwave.api.modules.accounts.Account;
import com.mailwave.api.modules.accounts.AccountService;
import com.mailwave.api.modules.folders.dtos.FolderCreateRequest;
import com.mailwave.api.modules.folders.dtos.FolderResponse;
import com.mailwave.api.modules.folders.dtos.FolderUpdateRequest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FolderService {


    private final FolderRepository folderRepository;
    private final AccountService accountService;

    public FolderService(FolderRepository folderRepository, AccountService accountService) {
        this.folderRepository = folderRepository;
        this.accountService = accountService;
    }

    // Criar uma nova pasta
    public FolderResponse createFolder(FolderCreateRequest model) {

        Folder folder = new Folder();
        folder.setFolderName(model.folderName());
        folder.setCreatedAt(LocalDateTime.now());

        //Recuperando Account do Banco de Dados
        Account account = accountService.getAccountById(model.accountId());
        folder.setAccount(account);

        Folder savedFolder = folderRepository.save(folder);

        return new FolderResponse(savedFolder);
    }

    // Buscar todas as pastas
    public List<FolderResponse> getAllFolders() {

        // Cria uma lista de registros(record) do tipo FolderResponse
        List<FolderResponse> accounts = new ArrayList<>();

        try{
            // Busca todos as contas na base e, para cada uma, cria um registro e adicona na lista
            List<Folder> results = folderRepository.findAll();

            // Se não houver registro, lança uma exceção
            if (results.isEmpty()) { throw new NoRecordsFoundException(); }

            // Para cada item retornado, cria um registro e adiciona na lista
            results.forEach(result -> accounts.add(new FolderResponse(result)));

            // Retorna a lista de registros
            return accounts;

        }catch (NoRecordsFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }

    }

    // Buscar uma pasta por ID
    public Folder getFolderById(Long id) {
        return folderRepository.findById(id).orElseThrow(() -> new FolderNotFoundException(id));
    }

    // Atualizar uma pasta existente
    public FolderResponse updateFolder(FolderUpdateRequest model) {

        try{
            Folder folder = folderRepository.findById(model.id()).orElseThrow(() -> new FolderNotFoundException(model.id()));
            Account account = accountService.getAccountById(model.accountId());

            folder.setFolderName(model.folderName());
            folder.setAccount(account);

            Folder updatedFolder = folderRepository.save(folder);
            return new FolderResponse(updatedFolder);
        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    // Deletar uma pasta
    public void deleteFolder(Long id) {
        Folder folder = folderRepository.findById(id).orElseThrow(() -> new FolderNotFoundException(id));

        try{
            folderRepository.delete(folder);
        }catch (DataIntegrityViolationException ex){
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }

    }
}
