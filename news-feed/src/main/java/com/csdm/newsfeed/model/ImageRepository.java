package com.csdm.newsfeed.model;

import com.csdm.newsfeed.model.dto.ImageDto;
import com.csdm.newsfeed.mapper.ImageMapper;
import com.csdm.newsfeed.model.dao.ImageDao;
import com.csdm.newsfeed.model.jpa.JpaImageRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.UUID;

/**
 * Repository class for Image
 */
@Component
@ApplicationScope
public class ImageRepository {

    private static final Logger LOG = Logger.getLogger(ImageRepository.class);

    @Autowired
    private JpaImageRepository jpaImageRepository;

    @Autowired
    private ImageMapper imageMapper;

    /**
     * Save an image
     * @param requestDao
     * @return the created image
     */
    public ImageDto saveImage(ImageDao requestDao) {

        if (requestDao != null) {
            ImageDao savedImage = jpaImageRepository.save(requestDao);
            LOG.info("Image saved!");
            return imageMapper.dao2dto(savedImage);
        } else {
            LOG.info("Image not saved!");
        }
        return new ImageDto();
    }

    /**
     * Find one image
     * @param id the requested image id
     * @return the read image
     */
    public ImageDto findOne(UUID id) {

        ImageDao imageDao = jpaImageRepository.getOne(id);
        if (imageDao != null) {
            LOG.info("Image found!");
            return imageMapper.dao2dto(imageDao);
        } else {
            LOG.info("Image not found!");
        }
        return new ImageDto();
    }

}
