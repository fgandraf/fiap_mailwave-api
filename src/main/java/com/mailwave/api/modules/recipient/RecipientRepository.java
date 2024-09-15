package com.mailwave.api.modules.recipient;

import com.mailwave.api.modules._sent.models.SentMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecipientRepository extends JpaRepository<Recipient, Long> {

    List<Recipient> getBySentMessage(SentMessage sentMessage);
}
