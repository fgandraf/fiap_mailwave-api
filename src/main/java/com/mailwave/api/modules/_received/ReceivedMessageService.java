package com.mailwave.api.modules._received;

import com.mailwave.api.exceptions.AccountNotFoundException;
import com.mailwave.api.exceptions.DatabaseOperationException;
import com.mailwave.api.exceptions.ReceivedMessageNotFoundException;
import com.mailwave.api.modules._received.dtos.message.ReceivedMessageCreateRequest;
import com.mailwave.api.modules._received.dtos.message.ReceivedMessageResponse;
import com.mailwave.api.modules._received.models.ReceivedMessage;
import com.mailwave.api.modules.accounts.AccountRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class ReceivedMessageService {

    private final ReceivedMessageRepository receivedMessageRepository;
    private final AccountRepository accountRepository;

    public ReceivedMessageService(ReceivedMessageRepository receivedMessageRepository, AccountRepository accountRepository) {
        this.receivedMessageRepository = receivedMessageRepository;
        this.accountRepository = accountRepository;
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


    public ReceivedMessageResponse getReceivedMessageById(Long id) {
        var receivedMessage = receivedMessageRepository.findById(id).orElseThrow(() -> new ReceivedMessageNotFoundException(id));
        return new ReceivedMessageResponse(receivedMessage);
    }

}
