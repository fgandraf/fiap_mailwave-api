package com.mailwave.api.modules.recipient;

import com.mailwave.api.exceptions.DatabaseOperationException;
import com.mailwave.api.exceptions.NoRecordsFoundException;
import com.mailwave.api.exceptions.SentMessageNotFoundException;
import com.mailwave.api.modules.recipient.dtos.RecipientCreateRequest;
import com.mailwave.api.modules.recipient.dtos.RecipientResponse;
import com.mailwave.api.modules.recipient.dtos.RecipientUpdateRequest;
import com.mailwave.api.modules.sentMessage.SentMessageRepository;
import com.mailwave.api.exceptions.RecipientNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;
    private final SentMessageRepository sentMessageRepository;

    public RecipientService(RecipientRepository recipientRepository, SentMessageRepository sentMessageRepository) {
        this.recipientRepository = recipientRepository;
        this.sentMessageRepository = sentMessageRepository;
    }

    public RecipientResponse createRecipient(RecipientCreateRequest request) {
        var sentMessage = sentMessageRepository.findById(request.messageId()).orElseThrow(() -> new SentMessageNotFoundException(request.messageId()));

        try {
            var recipient = new Recipient(
                    null,
                    request.recipientEmail(),
                    request.type(),
                    sentMessage
            );

            var savedRecipient = recipientRepository.save(recipient);

            return new RecipientResponse(savedRecipient);

        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public  List<RecipientResponse> getAllRecipientsBySentMessageId(Long messageId) {
        var sentMessage = sentMessageRepository.findById(messageId).orElseThrow(() -> new SentMessageNotFoundException(messageId));

        List<RecipientResponse> recipients = new ArrayList<>();

        try{
            var results = recipientRepository.getBySentMessage(sentMessage);

            if (results.isEmpty()) { throw new NoRecordsFoundException(); }

            results.forEach(result -> recipients.add(new RecipientResponse(result)));

            return recipients;

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao buscar destinatários.", ex);
        }
    }

    public RecipientResponse getRecipientById(Long id) {
        var recipient = recipientRepository.findById(id).orElseThrow(() -> new RecipientNotFoundException(id));
        return  new RecipientResponse(recipient);
    }

    public RecipientResponse updateRecipient(RecipientUpdateRequest request) {
        var recipient = recipientRepository.findById(request.id()).orElseThrow(()-> new RecipientNotFoundException(request.id()));
        var sentMessage = sentMessageRepository.findById(request.messageId()).orElseThrow(() -> new SentMessageNotFoundException(request.messageId()));

        try {
            recipient.setRecipientEmail(request.recipientEmail());
            recipient.setType(request.type());
            recipient.setSentMessage(sentMessage);

            var savedRecipient = recipientRepository.save(recipient);

            return new RecipientResponse(savedRecipient);

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public void deleteRecipient(Long id) {
        var recipient = recipientRepository.findById(id).orElseThrow(()-> new RecipientNotFoundException(id));

        try {
            recipientRepository.delete(recipient);

        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao deletar: pode haver dados relacionados.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao deletar.", ex);
        }
    }
}
