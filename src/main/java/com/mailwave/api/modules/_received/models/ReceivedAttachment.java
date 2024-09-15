package com.mailwave.api.modules._received.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TBL_RECEIVED_ATTACHMENTS")
public class ReceivedAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_RECEIVED_ATTACHMENTS")
    @SequenceGenerator(name = "SEQ_RECEIVED_ATTACHMENTS", sequenceName = "SEQ_RECEIVED_ATTACHMENTS", allocationSize = 1)
    @Column(name = "ATTACHMENT_ID")
    private Long id;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "FILE_TYPE")
    private String fileType;

    @Lob
    @Column(name = "FILE_DATA")
    private byte[] fileData;


    @ManyToOne
    @JoinColumn(name = "MESSAGE_ID", nullable = false)
    private ReceivedMessage receivedMessage;
}
