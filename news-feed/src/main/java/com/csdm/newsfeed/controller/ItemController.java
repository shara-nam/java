package com.csdm.newsfeed.controller;

import com.csdm.newsfeed.model.dto.ItemDto;
import com.csdm.newsfeed.model.ItemRepository;
import com.csdm.newsfeed.model.dao.ItemDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

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

}
