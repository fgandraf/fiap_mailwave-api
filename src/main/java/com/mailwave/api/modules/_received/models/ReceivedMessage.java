package com.mailwave.api.modules._received.models;

import com.mailwave.api.modules._sent.models.SentAttachment;
import com.mailwave.api.modules.accounts.Account;
import com.mailwave.api.modules.folders.Folder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TBL_RECEIVED_MESSAGES")
public class ReceivedMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECEIVED_MESSAGES")
    @SequenceGenerator(name = "SEQ_RECEIVED_MESSAGES", sequenceName = "SEQ_RECEIVED_MESSAGES", allocationSize = 1)
    @Column(name = "MESSAGE_ID")
    private Long id;

    @Column(name = "SENDER")
    private String sender;

    @Column(name = "SUBJECT")
    private String subject;

    @Lob
    @Column(name = "BODY")
    private String body;

    @Column(name = "RECEIVED_AT")
    private LocalDateTime receivedAt;

    @Column(name = "IS_READ")
    private Boolean isRead;



    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @ManyToOne
    @JoinColumn(name = "FOLDER_ID", nullable = false)
    private Folder folder;


    @OneToMany(mappedBy = "MESSAGE_ID")
    private List<SentAttachment> attachments;


    public ReceivedMessage(Long id, String sender, String subject, String body, LocalDateTime receivedAt, Boolean isRead, Account account, Folder folder, List<SentAttachment> attachments) {
        this.id = id;
        this.sender = sender;
        this.subject = subject;
        this.body = body;
        this.receivedAt = receivedAt;
        this.isRead = isRead;
        this.account = account;
        this.folder = folder;
        this.attachments = attachments;
    }

    public ReceivedMessage() {}
}
