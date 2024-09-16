package com.mailwave.api.modules.receivedMessage;

import com.mailwave.api.modules.receivedMessage.models.ReceivedMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReceivedMessageRepository extends JpaRepository<ReceivedMessage, Long> {

    @Query(value = "SELECT MESSAGE_ID, ACCOUNT_ID, SENDER, SUBJECT, RECEIVED_AT, IS_READ, FOLDER_ID " +
            "FROM TBL_RECEIVED_MESSAGES " +
            "WHERE ACCOUNT_ID = :accountId ORDER BY RECEIVED_AT", nativeQuery = true)
    List<Object[]> findAllIndexByAccountId(Long accountId);

    @Query(value = "SELECT MESSAGE_ID, ACCOUNT_ID, SENDER, SUBJECT, RECEIVED_AT, IS_READ, FOLDER_ID " +
            "FROM TBL_RECEIVED_MESSAGES " +
            "WHERE FOLDER_ID = :folderId ORDER BY RECEIVED_AT", nativeQuery = true)
    List<Object[]> findAllIndexByFolderId(Long folderId);


    @Query(value = "SELECT MESSAGE_ID, ACCOUNT_ID, SENDER, SUBJECT, RECEIVED_AT, IS_READ, FOLDER_ID " +
            "FROM TBL_RECEIVED_MESSAGES " +
            "WHERE ACCOUNT_ID = :accountId AND IS_READ = false ORDER BY RECEIVED_AT", nativeQuery = true)
    List<Object[]> findAllIndexUnreadByAccountId(Long accountId);

    @Query(value = "SELECT a.MESSAGE_ID, a.ACCOUNT_ID, a.SENDER, a.SUBJECT, a.RECEIVED_AT, a.IS_READ, a.FOLDER_ID " +
            "FROM TBL_RECEIVED_MESSAGES a " +
            "INNER JOIN TBL_RECEIVED_MESSAGE_TAGS b ON a.MESSAGE_ID = b.MESSAGE_ID " +
            "WHERE b.TAG_ID = :tagId ORDER BY a.RECEIVED_AT", nativeQuery = true)
    List<Object[]> findAllIndexByTagId(Long tagId);
}