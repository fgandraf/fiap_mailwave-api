package com.mailwave.api.modules.folders;

import com.mailwave.api.modules.accounts.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TBL_FOLDERS")
public class Folder {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FOLDERS")
    @SequenceGenerator(name = "SEQ_FOLDERS", sequenceName = "SEQ_FOLDERS", allocationSize = 1)
    @Column(name = "FOLDER_ID")
    private Long id;

    @Column(name = "FOLDER_NAME")
    private String folderName;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "ACCOUNT_ID", nullable = false)
    private Account account;

    public Folder(Long id, String folderName, LocalDateTime createdAt, Account account) {
        this.id = id;
        this.folderName = folderName;
        this.createdAt = createdAt;
        this.account = account;
    }

    public Folder() {}
}
