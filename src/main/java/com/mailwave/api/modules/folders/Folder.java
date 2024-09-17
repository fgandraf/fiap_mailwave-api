package com.mailwave.api.modules.folders;

import com.mailwave.api.modules.receivedMessage.models.ReceivedMessage;
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
@Table(name = "TBL_FOLDERS")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FOLDER_ID")
    private Long id;

    @Column(name = "FOLDER_NAME")
    private String folderName;

    @Column(name = "CREATED_AT", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReceivedMessage> receivedMessages;


    public Folder(Long id, String folderName, LocalDateTime createdAt, Account account, List<ReceivedMessage> receivedMessages) {
        this.id = id;
        this.folderName = folderName;
        this.createdAt = createdAt;
        this.account = account;
        this.receivedMessages = receivedMessages;
    }

    public Folder() {}
}
