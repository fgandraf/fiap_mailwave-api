package com.mailwave.api.modules.recipient;

import com.mailwave.api.modules.sentMessage.models.SentMessage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TBL_RECIPIENTS")
public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECIPIENTS")
    @SequenceGenerator(name = "SEQ_RECIPIENTS", sequenceName = "SEQ_RECIPIENTS", allocationSize = 1)
    @Column(name = "RECIPIENT_ID")
    private Long id;

    @Column(name = "RECIPIENT_EMAIL")
    private String recipientEmail;

    @Column(name = "TYPE")
    private String type; // TO, CC, BCC

    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID", nullable = false)
    private SentMessage sentMessage;

    public Recipient(Long id, String recipientEmail, String type, SentMessage sentMessage) {
        this.id = id;
        this.recipientEmail = recipientEmail;
        this.type = type;
        this.sentMessage = sentMessage;
    }

    public Recipient() {}
}
