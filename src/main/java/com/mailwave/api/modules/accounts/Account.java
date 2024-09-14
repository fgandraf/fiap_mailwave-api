package com.mailwave.api.modules.accounts;

import com.mailwave.api.modules.users.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TBL_ACCOUNTS")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_ACCOUNTS")
    @SequenceGenerator(name = "SEQ_ACCOUNTS", sequenceName = "SEQ_ACCOUNTS", allocationSize = 1)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "PROVIDER")
    private String provider;

    @Column(name = "PASSWORD_HASH")
    private String passwordHash;

    @Column(name = "INCOMING_SERVER")
    private String incomingServer;

    @Column(name = "INCOMING_PORT")
    private Integer incomingPort;

    @Column(name = "INCOMING_PROTOCOL")
    private String incomingProtocol;

    @Column(name = "OUTGOING_SERVER")
    private String outgoingServer;

    @Column(name = "OUTGOING_PORT")
    private Integer outgoingPort;

    @Column(name = "USE_SSL")
    private Boolean useSsl;

    @Column(name = "USE_TLS")
    private Boolean useTls;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    public Account(Long id, String emailAddress, String provider, String passwordHash, String incomingServer, Integer incomingPort, String incomingProtocol, String outgoingServer, Integer outgoingPort, Boolean useSsl, Boolean useTls, LocalDateTime createdAt, LocalDateTime updatedAt, User user) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.provider = provider;
        this.passwordHash = passwordHash;
        this.incomingServer = incomingServer;
        this.incomingPort = incomingPort;
        this.incomingProtocol = incomingProtocol;
        this.outgoingServer = outgoingServer;
        this.outgoingPort = outgoingPort;
        this.useSsl = useSsl;
        this.useTls = useTls;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.user = user;
    }

    public Account() {}
}