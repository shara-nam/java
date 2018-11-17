package com.csdm.newsfeed.model.dao;

import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table(name = "item")
@Data
public class ItemDao {

    @Id
    private UUID id;

    @Column(name = "title")
    private String title;

    @Type(type = "text")
    @Column(name = "description")
    private String description;

    @Column(name = "publication_date")
    private Timestamp publicationDate;

    @Lob
    @Column(name = "url_byte_array")
    private byte[] urlByteArray;
}
