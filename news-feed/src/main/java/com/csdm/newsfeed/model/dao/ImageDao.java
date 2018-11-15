package com.csdm.newsfeed.model.dao;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "image")
@Data
public class ImageDao {

    @Id
    private UUID id;

    @Column(name = "url")
    private String url;

    @Lob
    @Column(name = "stream", columnDefinition = "mediumblob")
    private byte[] imageStream;

    @OneToOne
    @JoinColumn(name = "item_id")
    private ItemDao item;
}
