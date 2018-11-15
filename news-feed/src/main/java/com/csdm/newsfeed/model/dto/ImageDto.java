package com.csdm.newsfeed.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ImageDto implements ServiceEntity {

    private String imageGuid;
    private String url;
    private String imageStream;

    @Override
    public String getId() {
        return this.imageGuid;
    }
}
