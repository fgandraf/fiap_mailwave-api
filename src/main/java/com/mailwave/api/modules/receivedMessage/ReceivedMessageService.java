package com.mailwave.api.modules.receivedMessage;

import com.mailwave.api.exceptions.*;
import com.mailwave.api.modules.messageTag.models.ReceivedMessageTag;
import com.mailwave.api.modules.messageTag.repositories.ReceivedMessageTagRepository;
import com.mailwave.api.modules.receivedMessage.dtos.ReceivedMessageCreateRequest;
import com.mailwave.api.modules.receivedMessage.dtos.ReceivedMessageIndexResponse;
import com.mailwave.api.modules.receivedMessage.dtos.ReceivedMessageResponse;
import com.mailwave.api.modules.receivedMessage.models.ReceivedMessage;
import com.mailwave.api.modules.accounts.AccountRepository;
import com.mailwave.api.modules.folders.FolderRepository;
import com.mailwave.api.modules.tags.TagRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReceivedMessageService {

    private final ReceivedMessageRepository receivedMessageRepository;
    private final AccountRepository accountRepository;
    private final FolderRepository folderRepository;
    private final TagRepository tagRepository;
    private final ReceivedMessageTagRepository receivedMessageTagRepository;


    public ReceivedMessageService(ReceivedMessageRepository receivedMessageRepository, AccountRepository accountRepository, FolderRepository folderRepository, TagRepository tagRepository, ReceivedMessageTagRepository receivedMessageTagRepository) {
        this.receivedMessageRepository = receivedMessageRepository;
        this.accountRepository = accountRepository;
        this.folderRepository = folderRepository;
        this.tagRepository = tagRepository;
        this.receivedMessageTagRepository = receivedMessageTagRepository;

    }

    public ReceivedMessageResponse createReceivedMessage(ReceivedMessageCreateRequest request) {
        var account = accountRepository.findById(request.accountId()).orElseThrow(() -> new AccountNotFoundException(request.accountId()));

        try {
            var message = new ReceivedMessage(
                    null,
                    request.sender(),
                    request.subject(),
                    request.body(),
                    LocalDateTime.now(),
                    false,
                    account,
                    null,
                    request.attachments()
            );

            var savedMessage = receivedMessageRepository.save(message);

            return new ReceivedMessageResponse(savedMessage);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public ReceivedMessageResponse moveMessageToFolder(Long messageId, Long folderId) {
        var receivedMessage = receivedMessageRepository.findById(messageId).orElseThrow(() -> new ReceivedMessageNotFoundException(messageId));
        var folder = folderRepository.findById(folderId).orElseThrow(() -> new FolderNotFoundException(folderId));

        try{
            receivedMessage.setFolder(folder);
            var updatedMessage = receivedMessageRepository.save(receivedMessage);
            return new ReceivedMessageResponse(updatedMessage);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public ReceivedMessageResponse addTagToReceivedMessage(Long messageId, Long tagId) {
        var receivedMessage = receivedMessageRepository.findById(messageId).orElseThrow(() -> new ReceivedMessageNotFoundException(messageId));
        var tag = tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));

        try{
            var messageTag = new ReceivedMessageTag(receivedMessage, tag);
            receivedMessageTagRepository.save(messageTag);
            return new ReceivedMessageResponse(receivedMessage);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public List<ReceivedMessageIndexResponse> getAllIndexFromTag(Long tagId) {
        List<ReceivedMessageIndexResponse> messages = new ArrayList<>();

        try{
            receivedMessageRepository.findAllIndexByTagId(tagId)
                    .forEach(result -> messages.add(objectToResponse(result)));
            return messages;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public List<ReceivedMessageIndexResponse> getAllIndexUnread(Long accountId) {
        List<ReceivedMessageIndexResponse> messages = new ArrayList<>();

        try{
            receivedMessageRepository.findAllIndexUnreadByAccountId(accountId)
                    .forEach(result -> messages.add(objectToResponse(result)));
            return messages;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public List<ReceivedMessageIndexResponse> getAllIndexByFolder(Long folderId) {
        List<ReceivedMessageIndexResponse> messages = new ArrayList<>();

        try{
            receivedMessageRepository.findAllIndexByFolderId(folderId)
                    .forEach(result -> messages.add(objectToResponse(result)));
            return messages;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public List<ReceivedMessageIndexResponse> getAllIndex(Long accountId) {
        List<ReceivedMessageIndexResponse> messages = new ArrayList<>();

        try{
           receivedMessageRepository.findAllIndexByAccountId(accountId)
                   .forEach(result -> messages.add(objectToResponse(result)));
           return messages;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public ReceivedMessageResponse getReceivedMessageById(Long id) {
        var receivedMessage = receivedMessageRepository.findById(id).orElseThrow(() -> new ReceivedMessageNotFoundException(id));
        return new ReceivedMessageResponse(receivedMessage);
    }

    public void deleteReceivedMessage(Long id){
        var receivedMessage = receivedMessageRepository.findById(id).orElseThrow(() -> new ReceivedMessageNotFoundException(id));

        try{
            receivedMessageRepository.delete(receivedMessage);

        }catch (DataIntegrityViolationException ex){
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }
    }

    private ReceivedMessageIndexResponse objectToResponse(Object[] result){
        var resultMessageId = (Long) result[0];
        var resultAccountId = (Long) result[1];
        var resultSender = (String) result[2];
        var resultSubject = (String) result[3];
        var resultReceivedAt = (LocalDateTime) result[4];
        var resultIsRead = (Boolean) result[5];
        var resultfolderId = (Long) result[6];

        return new ReceivedMessageIndexResponse(
                resultMessageId,
                resultSender,
                resultSubject,
                resultIsRead,
                resultReceivedAt,
                resultAccountId,
                resultfolderId);
    }

}
