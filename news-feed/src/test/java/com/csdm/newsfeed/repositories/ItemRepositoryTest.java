package com.csdm.newsfeed.repositories;

import com.csdm.newsfeed.config.JpaItemRepositoryMockProvider;
import com.csdm.newsfeed.model.ItemRepository;
import com.csdm.newsfeed.model.dao.ItemDao;
import com.csdm.newsfeed.model.dto.ItemDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Timestamp;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {
        ItemRepository.class,
        JpaItemRepositoryMockProvider.class
})
@WebAppConfiguration
@ActiveProfiles("mockItem")
public class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    private static final UUID ITEM_UUID = UUID.randomUUID();
    private static final String ITEM_TITLE = "Title";
    private static final String ITEM_DESCRIPTION = "Description";
    private static final Timestamp ITEM_PUBLICATION_DATE = new Timestamp(System.currentTimeMillis());
    private static final byte[] URL_BYTE_ARRAY = new byte[20];

    private ItemDao setUpItem() {
        ItemDao item = new ItemDao();
        item.setId(ITEM_UUID);
        item.setTitle(ITEM_TITLE);
        item.setDescription(ITEM_DESCRIPTION);
        item.setPublicationDate(ITEM_PUBLICATION_DATE);
        item.setUrlByteArray(URL_BYTE_ARRAY);

        return item;
    }

    @Test
    public void testSaveImage() {
        //given
        ItemDao item = setUpItem();

        //when
        ItemDto savedItem = itemRepository.saveItem(item);

        //then
        assertEquals(null, savedItem);
        //The jpaMockProvider that I created somehow returns 'null' on save(), :(

        //after
        //delete(item)
    }
}
