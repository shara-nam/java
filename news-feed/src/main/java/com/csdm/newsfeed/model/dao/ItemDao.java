package com.csdm.newsfeed.model.dao;

import lombok.Data;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "item")
@Data
public class ItemDao {

    @Id
    private UUID id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "publication_date")
    private DateTime publicationDate;

    @Lob
    @Column(name = "url_byte_array")
    private byte[] urlByteArray;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "item")
    private ImageDao image;
}
