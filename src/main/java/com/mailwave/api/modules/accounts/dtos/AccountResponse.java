package com.mailwave.api.modules.accounts.dtos;

import com.mailwave.api.modules.accounts.Account;
import java.time.LocalDateTime;

public record AccountResponse(
        Long id,
        String emailAddress,
        String provider,
        String incomingServer,
        Integer incomingPort,
        String incomingProtocol,
        String outgoingServer,
        Integer outgoingPort,
        Boolean useSsl,
        Boolean useTls,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long userId
){
    public AccountResponse(Account account) {
        this(
                account.getId(),
                account.getEmailAddress(),
                account.getProvider(),
                account.getIncomingServer(),
                account.getIncomingPort(),
                account.getIncomingProtocol(),
                account.getOutgoingServer(),
                account.getOutgoingPort(),
                account.getUseSsl(),
                account.getUseTls(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.getUser().getId()
        );
    }
}
