package com.mailwave.api.modules._sent;

import com.mailwave.api.modules._sent.models.SentMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentMessageRepository extends JpaRepository<SentMessage, Long> {

}
