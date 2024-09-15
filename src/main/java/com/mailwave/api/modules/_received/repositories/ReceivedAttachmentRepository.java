package com.mailwave.api.modules._received.repositories;

import com.mailwave.api.modules._received.models.ReceivedAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceivedAttachmentRepository extends JpaRepository<ReceivedAttachment, Long> {
}
