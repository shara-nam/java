package com.csdm.newsfeed.mapper;

import com.csdm.newsfeed.model.dto.ImageDto;
import com.csdm.newsfeed.model.dto.ItemDto;
import com.csdm.newsfeed.model.dao.ImageDao;
import com.csdm.newsfeed.model.dao.ItemDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.UUID;

@Mapper(uses = {MapperUtil.class, UUID.class})
public interface ItemMapper {

    @Mappings({
            @Mapping(target = "id", expression = "java(UUID.fromString(dto.getUuid()))"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "publicationDate", expression = "java(MapperUtil.map(dto.getPublicationDate()))"),
            @Mapping(target = "urlByteArray", expression = "java(MapperUtil.map(dto.getPublicationDate()))")
    })
    ItemDao dto2dao(ItemDto dto);

    @Mappings({
            @Mapping(target = "itemUuid", expression = "java(dao.getId() == null ? null : dao.getId().toString())"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "publicationDate", expression = "java(MapperUtil.map(dao.getPublicationDate()))")
    })
    ItemDto dao2dto(ItemDao dao);

    List<ItemDao> dtoList2DaoList(List<ItemDto> dtoList);

    List<ItemDto> daoList2DtoList(List<ItemDao> daoList);
}
