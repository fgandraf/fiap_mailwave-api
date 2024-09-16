package com.mailwave.api.modules.messageTag.repositories;

import com.mailwave.api.modules.messageTag.models.ReceivedMessageTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivedMessageTagRepository extends JpaRepository<ReceivedMessageTag, Long> {
}
