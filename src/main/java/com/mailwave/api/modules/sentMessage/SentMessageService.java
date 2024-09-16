package com.mailwave.api.modules.sentMessage;

import com.mailwave.api.exceptions.*;
import com.mailwave.api.modules.messageTag.models.SentMessageTag;
import com.mailwave.api.modules.messageTag.repositories.SentMessageTagRepository;
import com.mailwave.api.modules.sentMessage.dtos.SentMessageCreateRequest;
import com.mailwave.api.modules.sentMessage.dtos.SentMessageIndexResponse;
import com.mailwave.api.modules.sentMessage.dtos.SentMessageResponse;
import com.mailwave.api.modules.sentMessage.models.SentMessage;
import com.mailwave.api.modules.accounts.AccountRepository;
import com.mailwave.api.modules.folders.FolderRepository;
import com.mailwave.api.modules.tags.TagRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class SentMessageService {

    private final SentMessageRepository sentMessageRepository;
    private final AccountRepository accountRepository;
    private final FolderRepository folderRepository;
    private final TagRepository tagRepository;
    private final SentMessageTagRepository sentMessageTagRepository;
    
    public SentMessageService(SentMessageRepository sentMessageRepository, AccountRepository accountRepository, FolderRepository folderRepository, TagRepository tagRepository, SentMessageTagRepository sentMessageTagRepository) {
        this.sentMessageRepository = sentMessageRepository;
        this.accountRepository = accountRepository;
        this.folderRepository = folderRepository;
        this.tagRepository = tagRepository;
        this.sentMessageTagRepository = sentMessageTagRepository;
    }

    public SentMessageResponse createSentMessage(SentMessageCreateRequest request) {
        var account = accountRepository.findById(request.accountId()).orElseThrow(() -> new AccountNotFoundException(request.accountId()));

        try {
            var message = new SentMessage(
                    null,
                    request.subject(),
                    request.body(),
                    LocalDateTime.now(),
                    account,
                    null,
                    request.attachments()
            );

            var savedMessage = sentMessageRepository.save(message);

            return new SentMessageResponse(savedMessage);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public SentMessageResponse moveMessageToFolder(Long messageId, Long folderId) {
        var sentMessage = sentMessageRepository.findById(messageId).orElseThrow(() -> new SentMessageNotFoundException(messageId));
        var folder = folderRepository.findById(folderId).orElseThrow(() -> new FolderNotFoundException(folderId));

        try{
            sentMessage.setFolder(folder);
            var updatedMessage = sentMessageRepository.save(sentMessage);
            return new SentMessageResponse(updatedMessage);
        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }


    public SentMessageResponse addTagToSentMessage(Long messageId, Long tagId) {
        var sentMessage = sentMessageRepository.findById(messageId).orElseThrow(() -> new SentMessageNotFoundException(messageId));
        var tag = tagRepository.findById(tagId).orElseThrow(() -> new TagNotFoundException(tagId));

        try{
            var messageTag = new SentMessageTag(sentMessage, tag);
            sentMessageTagRepository.save(messageTag);
            return new SentMessageResponse(sentMessage);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public List<SentMessageIndexResponse> getAllIndexFromTag(Long tagId) {
        List<SentMessageIndexResponse> messages = new ArrayList<>();

        try{
            sentMessageRepository.findAllIndexByTagId(tagId)
                    .forEach(result -> messages.add(objectToResponse(result)));
            return messages;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }


    public List<SentMessageIndexResponse> getAllIndexByFolder(Long folderId) {
        List<SentMessageIndexResponse> messages = new ArrayList<>();

        try{
            sentMessageRepository.findAllIndexByFolderId(folderId)
                    .forEach(result -> messages.add(objectToResponse(result)));
            return messages;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public List<SentMessageIndexResponse> getAllIndex(Long accountId) {
        List<SentMessageIndexResponse> messages = new ArrayList<>();

        try{
           sentMessageRepository.findAllIndexByAccountId(accountId)
                   .forEach(result -> messages.add(objectToResponse(result)));
           return messages;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado.", ex);
        }
    }

    public SentMessageResponse getSentMessageById(Long id) {
        var sentMessage = sentMessageRepository.findById(id).orElseThrow(() -> new SentMessageNotFoundException(id));
        return new SentMessageResponse(sentMessage);
    }

    public void deleteSentMessage(Long id){
        var sentMessage = sentMessageRepository.findById(id).orElseThrow(() -> new SentMessageNotFoundException(id));

        try{
            sentMessageRepository.delete(sentMessage);

        }catch (DataIntegrityViolationException ex){
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        }catch (Exception ex){
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }
    }

    private SentMessageIndexResponse objectToResponse(Object[] result){
        var resultMessageId = (Long) result[0];
        var resultAccountId = (Long) result[1];
        var resultSubject = (String) result[2];
        var resultSentAt = (LocalDateTime) result[3];

        return new SentMessageIndexResponse(
                resultMessageId,
                resultSubject,
                resultSentAt,
                resultAccountId
                );
    }
}