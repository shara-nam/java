package com.csdm.newsfeed.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ItemDto implements ServiceEntity {

    private String itemUuid;
    private String title;
    private String description;
    private String publicationDate;
    private String urlByteArray;

    @Override
    public String getId() {
        return this.itemUuid;
    }
}
