package com.mailwave.api.modules.users;

import com.mailwave.api.modules.email.models.MessageReceived;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserReppository extends JpaRepository<User, Long> {

    // TO DO: Implements

}
