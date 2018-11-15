package com.csdm.newsfeed.model.jpa;

import com.csdm.newsfeed.model.dao.ItemDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaItemRepository extends JpaRepository<ItemDao, UUID> {
}
