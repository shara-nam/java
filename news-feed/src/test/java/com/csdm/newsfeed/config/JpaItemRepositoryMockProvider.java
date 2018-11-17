package com.csdm.newsfeed.config;

import com.csdm.newsfeed.mapper.ItemMapper;
import com.csdm.newsfeed.model.jpa.JpaItemRepository;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

/**
 * Internal - this will mock the repository and inject it
 * to the context provided by the xml that defines the RSS consumer.
 */
@Configuration
@Profile("mockItem")
public class JpaItemRepositoryMockProvider {

    @Bean
    @Primary
    public JpaItemRepository jpaItemRepository() {
        JpaItemRepository mockedJpaItemRepository = Mockito.mock(JpaItemRepository.class);

        return mockedJpaItemRepository;
    }

    @Bean
    @Primary
    public ItemMapper imageMapper() {
        return Mockito.mock(ItemMapper.class);
    }
}
