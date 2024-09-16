package com.mailwave.api.modules.sentMessage;

import com.mailwave.api.modules.sentMessage.models.SentMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface SentMessageRepository extends JpaRepository<SentMessage, Long> {

    @Query(value = "SELECT MESSAGE_ID, ACCOUNT_ID, SUBJECT, SENT_AT  " +
            "FROM TBL_SENT_MESSAGES " +
            "WHERE ACCOUNT_ID = :accountId ORDER BY SENT_AT", nativeQuery = true)
    List<Object[]> findAllIndexByAccountId(Long accountId);

    @Query(value = "SELECT MESSAGE_ID, ACCOUNT_ID, SUBJECT, SENT_AT  " +
            "FROM TBL_SENT_MESSAGES " +
            "WHERE FOLDER_ID = :folderId ORDER BY SENT_AT", nativeQuery = true)
    List<Object[]> findAllIndexByFolderId(Long folderId);

    @Query(value = "SELECT a.MESSAGE_ID, a.ACCOUNT_ID, a.SUBJECT, a.SENT_AT " +
            "FROM TBL_SENT_MESSAGES a " +
            "INNER JOIN TBL_SENT_MESSAGE_TAGS b ON a.MESSAGE_ID = b.MESSAGE_ID " +
            "WHERE b.TAG_ID = :tagId ORDER BY a.SENT_AT", nativeQuery = true)
    List<Object[]> findAllIndexByTagId(Long tagId);

}
