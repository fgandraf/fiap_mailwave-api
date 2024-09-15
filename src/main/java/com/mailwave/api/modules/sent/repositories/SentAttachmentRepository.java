package com.mailwave.api.modules.sent.repositories;

import com.mailwave.api.modules.sent.models.SentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentAttachmentRepository extends JpaRepository<SentAttachment, Long> {

}
