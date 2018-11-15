package com.csdm.newsfeed.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Handler class for calling methods from different micro services, via REST
 */
@Component
@Slf4j
public class EntityHandler {

    private static final String BASE_URL = "http://localhost:8130/entity";

    private static final Logger LOG = Logger.getLogger(EntityHandler.class);

    private RestTemplate restTemplate = new RestTemplate();

    /**
     * Example method for calling a micro service
     *
     * @param entityId the entityId
     * @return Object EntityDto
     */
    public Object getEntityById(String entityId) {
        ResponseEntity<Object> responseEntity;

        try {
            responseEntity = restTemplate.getForEntity(BASE_URL + "/" + entityId, Object.class);
            return responseEntity.getBody();
        } catch (Exception e) {
            LOG.error(e.getMessage());
        }

        return null;
    }
}
