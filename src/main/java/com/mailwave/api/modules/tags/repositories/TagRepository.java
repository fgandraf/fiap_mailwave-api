package com.mailwave.api.modules.tags.repositories;

import com.mailwave.api.modules.tags.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}
