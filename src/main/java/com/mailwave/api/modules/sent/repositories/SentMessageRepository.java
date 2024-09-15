package com.mailwave.api.modules.sent.repositories;

import com.mailwave.api.modules.sent.models.SentMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentMessageRepository extends JpaRepository<SentMessage, Long> {

}
