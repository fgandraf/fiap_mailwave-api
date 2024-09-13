package com.mailwave.api.modules.email.repositories;

import com.mailwave.api.modules.email.models.MessageReceived;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivedReppository extends JpaRepository<MessageReceived, Long> {

    // TO DO: Implements

}
