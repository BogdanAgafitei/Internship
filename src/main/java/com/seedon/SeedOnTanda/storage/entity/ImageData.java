package com.seedon.SeedOnTanda.storage.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seedon.SeedOnTanda.common.CommonBaseAbstractEntity;
import jakarta.persistence.*;
import lombok.*;
import org.dom4j.tree.AbstractEntity;

@Entity
@Data
@Builder
@Table(name = "image_data")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ImageData extends CommonBaseAbstractEntity {

    @Column(name = "file_name")
    private String fileName;
    private String filePath;
    private String owner;
    @Column(name = "type")
    private String contentType;
    @Column(name = "checksum")
    private String checksum;
    @Lob
    @Column(name = "imageData")
    @JsonIgnore
    private byte[] imageData;
    private long size;
    private String url;
    @Transient
    private ImageResolution imageResolution;
    private boolean isPrivate;

}