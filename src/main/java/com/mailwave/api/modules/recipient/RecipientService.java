package com.mailwave.api.modules.recipient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecipientService {

    @Autowired
    private RecipientRepository recipientRepository;

    // Criar um novo destinatário
    public Recipient createRecipient(Recipient recipient) {
        return recipientRepository.save(recipient);
    }

    // Buscar todos os destinatários
    public List<Recipient> getAllRecipients() {
        return recipientRepository.findAll();
    }

    // Buscar um destinatário por ID
    public Optional<Recipient> getRecipientById(Long id) {
        return recipientRepository.findById(id);
    }

    // Atualizar um destinatário existente
    public Optional<Recipient> updateRecipient(Long id, Recipient recipientDetails) {
        return recipientRepository.findById(id).map(recipient -> {
            recipient.setRecipientEmail(recipientDetails.getRecipientEmail());
            recipient.setType(recipientDetails.getType());
            return recipientRepository.save(recipient);
        });
    }

    // Deletar um destinatário
    public boolean deleteRecipient(Long id) {
        return recipientRepository.findById(id).map(recipient -> {
            recipientRepository.delete(recipient);
            return true;
        }).orElse(false);
    }
}
