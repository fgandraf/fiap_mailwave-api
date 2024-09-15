package com.mailwave.api.modules.accounts.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record AccountCreateRequest(

        @NotNull(message = "Email address is required.")
        @Email(message = "Invalid email address")
        @Size(min = 1, max = 255, message = "Email address must be between 1 and 255 characters.")
        String emailAddress,

        @NotNull(message = "Provider is required.")
        @Size(min = 1, max = 100, message = "Provider must be between 1 and 100 characters.")
        String provider,

        @NotNull(message = "Password is required.")
        @Size(min = 6, max = 20, message = "Password must be between 6 and 20 characters")
        String password,

        String incomingServer,
        Integer incomingPort,
        String incomingProtocol,
        String outgoingServer,
        Integer outgoingPort,
        Boolean useSsl,
        Boolean useTls,

        @NotNull(message = "User Id is required.")
        Long userId
){}
