package com.csdm.newsfeed.controller;

import com.csdm.newsfeed.model.dao.ItemDao;
import com.rometools.rome.feed.synd.SyndEnclosure;
import com.rometools.rome.feed.synd.SyndEntry;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Timestamp;
import java.util.UUID;

@Slf4j
@Controller
public class SyndicationController {

    private static final Logger LOG = Logger.getLogger(SyndicationController.class);

    @Autowired
    private ItemController itemController;

    public HttpStatus handleMessage(Message<SyndEntry> message) throws Exception {
        SyndEntry entry = message.getPayload();
        UUID uuid = message.getHeaders().getId();

        ItemDao item = new ItemDao();
        item.setId(uuid);
        item.setTitle(entry.getTitle());
        item.setDescription(entry.getDescription().getValue());
        item.setPublicationDate(new Timestamp(entry.getPublishedDate().getTime()));

        SyndEnclosure image = entry.getEnclosures().get(0);

        if(StringUtils.isNotEmpty(image.getUrl())) {
            item.setUrlByteArray(fetchRemoteFile(image.getUrl()));
        }

        if (!itemController.exists(item)) {
            HttpStatus saveItemStatus = itemController.saveItem(item);
            return saveItemStatus;
        } else {
            LOG.debug("Message with UUID " + uuid + " already exists!");
            return HttpStatus.CONFLICT;
        }

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
            LOG.debug("Url to byteArray exception: please add meaningful exception handling " + SyndicationController.class);
        }
        finally {
            if (is != null) is.close();
        }
        return bytes;
    }

}
