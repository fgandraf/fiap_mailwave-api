package com.mailwave.api.modules._messageTag.models;

import com.mailwave.api.modules.received.models.ReceivedMessage;
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
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECEIVED_MESSAGE_TAGS")
    @SequenceGenerator(name = "SEQ_RECEIVED_MESSAGE_TAGS", sequenceName = "SEQ_RECEIVED_MESSAGE_TAGS", allocationSize = 1)
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
