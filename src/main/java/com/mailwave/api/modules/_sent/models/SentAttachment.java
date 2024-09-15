package com.mailwave.api.modules._sent.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.EqualsAndHashCode;

@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "TBL_SENT_ATTACHMENTS")
public class SentAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SENT_ATTACHMENTS")
    @SequenceGenerator(name = "SEQ_SENT_ATTACHMENTS", sequenceName = "SEQ_SENT_ATTACHMENTS", allocationSize = 1)
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
    private SentMessage sentMessage;
}
