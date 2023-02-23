package com.seedon.SeedOnTanda.storage.entity;

import com.seedon.SeedOnTanda.common.CommonBaseAbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "storage_files")
public class StorageFiles extends CommonBaseAbstractEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "checksum")
    private long checksum;

    @Column(name = "data")
    @Lob
    private byte[] data;
}
