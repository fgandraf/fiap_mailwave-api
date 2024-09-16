package com.mailwave.api.modules.tags;

import com.mailwave.api.modules._received.models.ReceivedMessage;
import com.mailwave.api.modules.accounts.Account;
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
@Table(name = "TBL_TAGS")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TAGS")
    @SequenceGenerator(name = "SEQ_TAGS", sequenceName = "SEQ_TAGS", allocationSize = 1)
    @Column(name = "TAG_ID")
    private Long id;

    @Column(name = "TAG_NAME")
    private String tagName;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceivedMessage> receivedMessages;

    public Tag(Long id, String tagName, LocalDateTime createdAt, Account account, List<ReceivedMessage> receivedMessages) {
        this.id = id;
        this.tagName = tagName;
        this.createdAt = createdAt;
        this.account = account;
        this.receivedMessages = receivedMessages;
    }

    public Tag() {}
}
