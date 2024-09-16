package com.mailwave.api.modules.messageTag.repositories;

import com.mailwave.api.modules.messageTag.models.SentMessageTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentMessageTagRepository extends JpaRepository<SentMessageTag, Long> {
}
