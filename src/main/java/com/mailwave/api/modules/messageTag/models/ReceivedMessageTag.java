package com.mailwave.api.modules.messageTag.models;

import com.mailwave.api.modules.receivedMessage.models.ReceivedMessage;
import com.mailwave.api.modules.tags.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TBL_RECEIVED_MESSAGE_TAGS")
public class ReceivedMessageTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MESSAGE_TAG_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID", nullable = false)
    private ReceivedMessage message;

    @ManyToOne
    @JoinColumn(name = "TAG_ID", nullable = false)
    private Tag tag;

    public ReceivedMessageTag(ReceivedMessage message, Tag tag) {
        this.message = message;
        this.tag = tag;
    }

    public ReceivedMessageTag() {}
}
