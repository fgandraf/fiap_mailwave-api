package com.mailwave.api.modules.messageTag.models;

import com.mailwave.api.modules.sentMessage.models.SentMessage;
import com.mailwave.api.modules.tags.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TBL_SENT_MESSAGE_TAGS")
public class SentMessageTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SENT_MESSAGE_TAGS")
    @SequenceGenerator(name = "SEQ_SENT_MESSAGE_TAGS", sequenceName = "SEQ_SENT_MESSAGE_TAGS", allocationSize = 1)
    @Column(name = "MESSAGE_TAG_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID", nullable = false)
    private SentMessage message;

    @ManyToOne
    @JoinColumn(name = "TAG_ID", nullable = false)
    private Tag tag;

    public SentMessageTag(SentMessage message, Tag tag) {
        this.message = message;
        this.tag = tag;
    }

    public SentMessageTag() {}
}
