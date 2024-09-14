package com.mailwave.api.modules.tags.models;

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
}
