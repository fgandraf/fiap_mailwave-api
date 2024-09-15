package com.mailwave.api.modules._messageTag;

import com.mailwave.api.modules._received.models.ReceivedMessage;
import com.mailwave.api.modules.tags.Tag;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TBL_MESSAGE_TAGS")
public class MessageTag {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MESSAGE_TAGS")
    @SequenceGenerator(name = "SEQ_MESSAGE_TAGS", sequenceName = "SEQ_MESSAGE_TAGS", allocationSize = 1)
    @Column(name = "ID")
    private Long id;



    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID", nullable = false)
    private ReceivedMessage receivedMessage;

    @ManyToOne
    @JoinColumn(name = "TAG_ID", nullable = false)
    private Tag tag;
}
