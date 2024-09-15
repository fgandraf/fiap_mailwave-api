package com.mailwave.api.modules.tags.dtos;

import com.mailwave.api.modules.tags.Tag;

import java.time.LocalDateTime;

public record TagResponse(
        Long id,
        String tagName,
        LocalDateTime createdAt,
        Long accountId
) {
    public TagResponse(Tag tag){
        this(
            tag.getId(),
            tag.getTagName(),
            tag.getCreatedAt(),
            tag.getAccount().getId()
        );
    }
}
