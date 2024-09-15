package com.mailwave.api.modules._sent.repositories;

import com.mailwave.api.modules._sent.models.SentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentAttachmentRepository extends JpaRepository<SentAttachment, Long> {

}
