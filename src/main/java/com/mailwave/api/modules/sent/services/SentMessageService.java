package com.mailwave.api.modules.sent.services;

import com.mailwave.api.modules.accounts.Account;
import com.mailwave.api.modules.accounts.AccountService;
import com.mailwave.api.modules.folders.FolderService;
import com.mailwave.api.modules.sent.dtos.message.SentMessageRequest;
import com.mailwave.api.modules.sent.dtos.message.SentMessageResponse;
import com.mailwave.api.modules.sent.models.SentMessage;
import com.mailwave.api.modules.sent.repositories.SentMessageRepository;
import com.mailwave.api.exceptions.SentMessageNotFoundException;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class SentMessageService {
    private final SentMessageRepository sentMessageRepository;
    private final AccountService accountService;
    private final FolderService folderService;

    public SentMessageService(SentMessageRepository sentMessageRepository, AccountService accountService, FolderService folderService) {
        this.sentMessageRepository = sentMessageRepository;
        this.accountService = accountService;
        this.folderService = folderService;
    }

    public SentMessageResponse sendMessage(SentMessageRequest request) {
        var accountResponse = accountService.getAccountById(request.accountId());
        var account = new Account(
                accountResponse.id(),
                accountResponse.emailAddress(),
                accountResponse.provider(),
                null,
                accountResponse.incomingServer(),
                accountResponse.incomingPort(),
                accountResponse.incomingProtocol(),
                accountResponse.outgoingServer(),
                accountResponse.outgoingPort(),
                accountResponse.useSsl(),
                accountResponse.useTls(),
                accountResponse.createdAt(),
                accountResponse.updatedAt(),
                null
        );

        var folder = request.folderId() != null ? folderService.getFolderById(request.folderId()).orElse(null) : null;

        var sentMessage = new SentMessage();
        sentMessage.setAccount(account);
        sentMessage.setFolder(folder);
        sentMessage.setSubject(request.subject());
        sentMessage.setBody(request.body());
        sentMessage.setSentAt(LocalDateTime.now());

        var savedMessage = sentMessageRepository.save(sentMessage);

        return new SentMessageResponse(savedMessage.getId(), savedMessage.getSubject(), savedMessage.getBody(), savedMessage.getSentAt());
    }

    public SentMessage getSentMessageById(Long id) {
        return sentMessageRepository.findById(id)
                .orElseThrow(() -> new SentMessageNotFoundException(id));
    }
}
