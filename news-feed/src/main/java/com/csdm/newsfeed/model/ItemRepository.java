package com.csdm.newsfeed.model;

import com.csdm.newsfeed.model.dto.ItemDto;
import com.csdm.newsfeed.mapper.ItemMapper;
import com.csdm.newsfeed.model.dao.ItemDao;
import com.csdm.newsfeed.model.jpa.JpaItemRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;
import java.util.UUID;

/**
 * Repository class for Item
 */
@Component
@ApplicationScope
public class ItemRepository {

    private static final Logger LOG = Logger.getLogger(ItemRepository.class);

    @Autowired
    private JpaItemRepository jpaItemRepository;

    @Autowired
    private ItemMapper itemMapper;

    /**
     * Save an item
     * @param requestDao
     * @return the created item
     */
    public ItemDto saveItem(ItemDao requestDao) {

        if (requestDao != null) {
            ItemDao savedItem = jpaItemRepository.save(requestDao);
            LOG.info("Item saved!");
            return itemMapper.dao2dto(savedItem);
        } else {
            LOG.info("Item not saved!");
            return new ItemDto();
        }
    }

    /**
     * Find all items
     * @return the list of items
     */
    public List<ItemDto> findAll() {

        List<ItemDao> daoItems = jpaItemRepository.findAll();
        if (daoItems != null) {
            LOG.info("A total count of " + daoItems.size() + " Items found!");
        } else {
            LOG.info("Items not found!");
        }
        return itemMapper.daoList2DtoList(daoItems);
    }

    /**
     * Find one item
     * @param id the requested item id
     * @return the read item
     */
    public ItemDto findOne(String id) {

        ItemDao itemDao = jpaItemRepository.getOne(UUID.fromString(id));
        if (itemDao != null) {
            LOG.info("Item found!");
        } else {
            LOG.info("Item not found!");
        }
        return new ItemDto();
    }

    public boolean exists(ItemDao requestDao) {

        if (requestDao != null) {
            if (findOne(requestDao.getId().toString()) != null);
            return true;
        }
        return false;
    }
}
