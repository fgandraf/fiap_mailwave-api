package com.mailwave.api.modules.received.services;

import com.mailwave.api.modules.received.models.ReceivedMessage;
import com.mailwave.api.modules.received.repositories.ReceivedMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReceivedMessageService {

    private final ReceivedMessageRepository receivedMessageRepository;

    @Autowired
    public ReceivedMessageService(ReceivedMessageRepository receivedMessageRepository) {
        this.receivedMessageRepository = receivedMessageRepository;
    }

    public ReceivedMessage saveMessage(ReceivedMessage message) {
        return receivedMessageRepository.save(message);
    }

    public Optional<ReceivedMessage> getMessageById(Long id) {
        return receivedMessageRepository.findById(id);
    }

    public Optional<ReceivedMessage> updateMessage(Long id, ReceivedMessage messageDetails) {
        if (receivedMessageRepository.existsById(id)) {
            messageDetails.setId(id);
            ReceivedMessage updatedMessage = receivedMessageRepository.save(messageDetails);
            return Optional.of(updatedMessage);
        }
        return Optional.empty();
    }

    public List<ReceivedMessage> getAllMessages() {
        return receivedMessageRepository.findAll();
    }

    public boolean deleteMessage(Long id) {
        if (receivedMessageRepository.existsById(id)) {
            receivedMessageRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
