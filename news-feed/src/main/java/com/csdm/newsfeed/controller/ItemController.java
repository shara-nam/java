package com.csdm.newsfeed.controller;

import com.csdm.newsfeed.client.EntityHandler;
import com.csdm.newsfeed.model.dto.ItemDto;
import com.csdm.newsfeed.model.ItemRepository;
import com.csdm.newsfeed.model.dao.ItemDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ItemController {

    private static final Logger LOG = Logger.getLogger(ItemController.class);

    @Autowired
    private ItemRepository itemRepository;

    public HttpStatus saveItem(ItemDao itemDao) {
        ItemDto createdItem = itemRepository.saveItem(itemDao);

        if (createdItem != null) {

            LOG.info("Item is created with the UUID: " + createdItem.getId());
            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }

    /**
     * By calling the findOne, this method checks for duplicates
     *
     * @param requestDao
     * @return
     */
    public boolean exists(ItemDao requestDao) {

        if (requestDao != null) {
            if (itemRepository.findOne(requestDao.getId().toString()) != null)
                return true;
            else
                return false;
        }
        return false;
    }

    @Autowired
    private EntityHandler entityHandler;

    @GetMapping
    public ResponseEntity<List<ItemDto>> findAll() {
        List<ItemDto> items = itemRepository.findAll();

        if (items != null) {
            LOG.info(items.size() + " items were found !");

            return new ResponseEntity<>(items, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> readItem(@PathVariable("id") String itemUuid) {
        ItemDto itemDto = itemRepository.findOne(itemUuid);

        if (itemDto != null) {

            // scale by calling different micro services, eg: entityHandler.getEntityById()

            return new ResponseEntity<>(itemDto, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
