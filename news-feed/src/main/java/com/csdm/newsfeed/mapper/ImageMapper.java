package com.csdm.newsfeed.mapper;

import com.csdm.newsfeed.model.dto.ImageDto;
import com.csdm.newsfeed.model.dao.ImageDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface ImageMapper {

    @Mappings({
            @Mapping(target = "id", source = "imageGuid"),
            @Mapping(target = "url", source = "url"),
            @Mapping(target = "imageStream", ignore = true),
    })
    ImageDao dto2dao(ImageDto dto);

    @Mappings({
            @Mapping(target = "imageGuid", expression = "java(dao.getId() == null ? null : dao.getId().toString())"),
            @Mapping(target = "url", source = "url"),
            @Mapping(target = "imageStream", ignore = true),
            @Mapping(target = "item", ignore = true)
    })
    ImageDto dao2dto(ImageDao dao);

    List<ImageDao> dtoList2DaoList(List<ImageDto> dtoList);

    List<ImageDto> daoList2DtoList(List<ImageDao> daoList);
}
