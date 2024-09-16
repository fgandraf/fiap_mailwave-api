package com.mailwave.api.modules._messageTag.repositories;

import com.mailwave.api.modules._messageTag.models.ReceivedMessageTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceivedMessageTagRepository extends JpaRepository<ReceivedMessageTag, Long> {
}
