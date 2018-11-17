package com.csdm.newsfeed.mapper;

import com.csdm.newsfeed.model.dto.ItemDto;
import com.csdm.newsfeed.model.dao.ItemDao;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", imports = {StandardCharsets.class, UUID.class, MapperUtil.class})
public interface ItemMapper {

    @Mappings({
            @Mapping(target = "id", expression = "java(UUID.fromString(dto.getItemUuid()))"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "publicationDate", expression = "java(MapperUtil.mapFromString(dto.getPublicationDate()))"),
            @Mapping(target = "urlByteArray", expression = "java(dto.getUrlByteArray().getBytes(StandardCharsets.UTF_8))")
    })
    ItemDao dto2dao(ItemDto dto);

    @Mappings({
            @Mapping(target = "itemUuid", expression = "java(dao.getId() == null ? null : dao.getId().toString())"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "description", source = "description"),
            @Mapping(target = "publicationDate", expression = "java(MapperUtil.mapFromTimestamp(dao.getPublicationDate()))"),
            @Mapping(target = "urlByteArray", expression = "java(new String(dao.getUrlByteArray()))")
    })
    ItemDto dao2dto(ItemDao dao);

    List<ItemDao> dtoList2DaoList(List<ItemDto> dtoList);

    List<ItemDto> daoList2DtoList(List<ItemDao> daoList);
}
