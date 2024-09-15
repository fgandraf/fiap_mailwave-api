package com.mailwave.api.modules.users.dtos;

import com.mailwave.api.modules.users.User;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
){
    public UserResponse(User user) {
        this(
                user.getId(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}