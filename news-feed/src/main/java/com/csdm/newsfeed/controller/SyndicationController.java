package com.csdm.newsfeed.controller;

import com.csdm.newsfeed.client.EntityHandler;
import com.csdm.newsfeed.model.dto.ItemDto;
import com.csdm.newsfeed.model.ItemRepository;
import com.csdm.newsfeed.model.dao.ImageDao;
import com.csdm.newsfeed.model.dao.ItemDao;
import com.csdm.newsfeed.util.BeanUtil;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/feed")
@Slf4j
public class SyndicationController {

    private static final Logger LOG = Logger.getLogger(SyndicationController.class);

    @Autowired
    private ItemRepository itemRepository;

    public void handleMessage(Message<SyndEntry> message) throws Exception {
        SyndEntry entry = message.getPayload();
        UUID uuid = message.getHeaders().getId();

        ItemDao item = new ItemDao();
        item.setId(uuid);
        item.setTitle(entry.getTitle());
        item.setDescription(entry.getDescription().getValue());
        item.setPublicationDate(new DateTime(entry.getPublishedDate()));

        ImageDao image = new ImageDao();
        image.setUrl(entry.getEnclosures().get(0).getUrl());

        if(StringUtils.isNotEmpty(image.getUrl())) {
            image.setId(uuid);
            image.setImageStream(fetchRemoteFile(image.getUrl()));

            HttpStatus saveImageStatus = BeanUtil.getBean(ImageController.class).saveImage(image);
            item.setImage(image);
            item.setUrlByteArray(image.getImageStream());
        }

        //if (!itemRepository.exists(item)) {}
        HttpStatus saveItemStatus = BeanUtil.getBean(ItemController.class).saveItem(item);
    }

    /**
     *
     * @param location resource url
     * @return bytes
     * @throws Exception
     */
    private byte[] fetchRemoteFile(String location) throws Exception {
        URL url = new URL(location);
        InputStream is = null;
        byte[] bytes = null;
        try {
            is = url.openStream ();
            bytes = IOUtils.toByteArray(is);
        } catch (IOException e) {
            LOG.debug("Non meaningful exception handling");
        }
        finally {
            if (is != null) is.close();
        }
        return bytes;
    }

    @Autowired
    private EntityHandler entityHandler;

    @GetMapping
    public ResponseEntity<List<ItemDto>> findAll() {
        List<ItemDto> items = itemRepository.findAll();

        if (items != null) {

            LOG.info(items.size() + " items were found !");

            // scale by calling different micro services, eg: entityHandler.getEntityById()
            // this is not a @RestController, but it's a good example for returning a call

            return new ResponseEntity<>(items, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemDto> readItem(@PathVariable("id") String id) {
        ItemDto itemDto = itemRepository.findOne(id);

        if (itemDto != null) {
            return new ResponseEntity<>(itemDto, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
