package com.mailwave.api.modules._sent.services;

import com.mailwave.api.exceptions.AccountNotFoundException;
import com.mailwave.api.exceptions.DatabaseOperationException;
import com.mailwave.api.modules.accounts.AccountRepository;
import com.mailwave.api.modules.folders.FolderRepository;
import com.mailwave.api.modules._sent.dtos.message.SentMessageRequest;
import com.mailwave.api.modules._sent.dtos.message.SentMessageResponse;
import com.mailwave.api.modules._sent.models.SentMessage;
import com.mailwave.api.modules._sent.repositories.SentMessageRepository;
import com.mailwave.api.exceptions.SentMessageNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class SentMessageService {
    private final SentMessageRepository sentMessageRepository;
    private final AccountRepository accountRepository;
    private final FolderRepository folderRepository;

   public SentMessageService(SentMessageRepository sentMessageRepository, AccountRepository accountRepository, FolderRepository folderRepository) {
       this.sentMessageRepository = sentMessageRepository;
       this.accountRepository = accountRepository;
       this.folderRepository = folderRepository;
   }

    public SentMessageResponse createSentMessage(SentMessageRequest request) {
        var account = accountRepository.findById(request.accountId()).orElseThrow(() -> new AccountNotFoundException(request.accountId()));
        var folder = request.folderId() != null ? folderRepository.findById(request.folderId()).orElse(null) : null;

        try{
            var messageToSent = new SentMessage(
                    null,
                    request.subject(),
                    request.body(),
                    request.sentAt(),
                    account,
                    folder
            );

            var sentMessage = sentMessageRepository.save(messageToSent);

            return new SentMessageResponse(sentMessage);

        }catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }


    public SentMessageResponse getSentMessageById(Long id) {
       var sentMessage = sentMessageRepository.findById(id).orElseThrow(() -> new SentMessageNotFoundException(id));
       return new SentMessageResponse(sentMessage);
    }
}
