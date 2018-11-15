package com.csdm.newsfeed.model.jpa;

import com.csdm.newsfeed.model.dao.ImageDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JpaImageRepository extends JpaRepository<ImageDao, UUID> {
}
