package com.seedon.SeedOnTanda.storage.repository;

import com.seedon.SeedOnTanda.storage.entity.StorageFiles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageFilesRepository extends JpaRepository<StorageFiles,String> {

}
