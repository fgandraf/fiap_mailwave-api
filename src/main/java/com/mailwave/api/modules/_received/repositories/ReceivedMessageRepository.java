package com.mailwave.api.modules._received.repositories;

import com.mailwave.api.modules._received.models.ReceivedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivedMessageRepository extends JpaRepository<ReceivedMessage, Long> {
}
