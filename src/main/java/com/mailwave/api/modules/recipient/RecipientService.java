package com.mailwave.api.modules.recipient;

import com.mailwave.api.exceptions.DatabaseOperationException;
import com.mailwave.api.exceptions.NoRecordsFoundException;
import com.mailwave.api.modules.recipient.dtos.RecipientRequest;
import com.mailwave.api.modules.recipient.dtos.RecipientResponse;
import com.mailwave.api.modules.sent.services.SentMessageService;
import com.mailwave.api.exceptions.RecipientNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipientService {

    private final RecipientRepository recipientRepository;
    private final SentMessageService sentMessageService;
    public RecipientService(RecipientRepository recipientRepository, SentMessageService sentMessageService) {
        this.recipientRepository = recipientRepository;
        this.sentMessageService = sentMessageService;
    }

    @Transactional
    public RecipientResponse createRecipient(RecipientRequest model) {
        try {
            var sentMessage = sentMessageService.getSentMessageById(model.messageId());

            var recipient = new Recipient();
            recipient.setRecipientEmail(model.recipientEmail());
            recipient.setType(model.type());
            recipient.setSentMessage(sentMessage);

            var savedRecipient = recipientRepository.save(recipient);
            return new RecipientResponse(savedRecipient.getId(), savedRecipient.getRecipientEmail(), savedRecipient.getType(), savedRecipient.getSentMessage().getId());
        } catch (DataIntegrityViolationException ex) {
            throw new DatabaseOperationException("Erro ao salvar: dados inválidos ou em conflito.", ex);
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao salvar.", ex);
        }
    }

    public  List<RecipientResponse> getAllRecipients() {
        var recipients = new ArrayList<RecipientResponse>();

        try{
            var results = recipientRepository.findAll();

            if (results.isEmpty()) {
                throw new NoRecordsFoundException();
            }

            results.forEach(result -> recipients.add(new RecipientResponse(result.getId(), result.getRecipientEmail(),result.getType(),result.getSentMessage().getId())));

            return recipients;
        } catch (NoRecordsFoundException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro inesperado ao buscar destinatários.", ex);
        }
    }

    public RecipientResponse getRecipientById(Long id) {
        var recipient = recipientRepository.findById(id).orElseThrow(() -> new RecipientNotFoundException(id));
        return  new RecipientResponse(recipient.getId(), recipient.getRecipientEmail(), recipient.getType(), recipient.getSentMessage().getId());
    }

    public RecipientResponse updateRecipient(RecipientRequest model, Long id) {
        try {
            var recipient = recipientRepository.findById(id).orElseThrow(()-> new RecipientNotFoundException(id));

            var sentMessage = sentMessageService.getSentMessageById(model.getSentMessageId());

            recipient.setRecipientEmail(model.recipientEmail());
            recipient.setType(model.type());
            recipient.setSentMessage(sentMessage);

            var savedRecipient = recipientRepository.save(recipient);

            return new RecipientResponse(savedRecipient.getId(), savedRecipient.getRecipientEmail(), savedRecipient.getType(), savedRecipient.getSentMessage().getId());

        } catch (Exception ex) {
            throw new DatabaseOperationException("Erro ao atualizar o destinatário.", ex);
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
