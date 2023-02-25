package com.seedon.SeedOnTanda.storage.repository;

import com.seedon.SeedOnTanda.storage.entity.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<ImageData,String> {

    @Transactional
    Optional<ImageData> findByFileName(String name);
}
