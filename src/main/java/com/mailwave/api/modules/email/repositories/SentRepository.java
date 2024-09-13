package com.mailwave.api.modules.email.repositories;

import com.mailwave.api.modules.email.models.MessageSent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SentRepository extends JpaRepository<MessageSent, Long> {

    // TO DO: Implements

}
