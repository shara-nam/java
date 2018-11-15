package com.csdm.newsfeed.controller;

import com.csdm.newsfeed.model.dto.ImageDto;
import com.csdm.newsfeed.model.ImageRepository;
import com.csdm.newsfeed.model.dao.ImageDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;

@Controller
public class ImageController {

    private static final Logger LOG = Logger.getLogger(ImageController.class);

    @Autowired
    private ImageRepository imageRepository;

    public HttpStatus saveImage(ImageDao imageDao) {
        ImageDto createdImage = imageRepository.saveImage(imageDao);

        if (createdImage != null) {

            LOG.info("Item is created with the UUID: " + createdImage.getId());
            return HttpStatus.OK;
        }

        return HttpStatus.BAD_REQUEST;
    }
}
