package com.seedon.SeedOnTanda.storage.service;

import com.seedon.SeedOnTanda.properties.StorageProperties;
import com.seedon.SeedOnTanda.storage.entity.ImageData;
import com.seedon.SeedOnTanda.storage.entity.ImageResolution;
import com.seedon.SeedOnTanda.storage.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;
    private final StorageProperties storageProperties;

    public ImageData store(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return imageRepository.save(getImageData(file));
    }

    public ImageData getInfoByImageByName(String name) {
        Optional<ImageData> dbImage = imageRepository.findByFileName(name);
        return ImageData.builder()
                .fileName(dbImage.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File Not Found!")).getFileName())
                .contentType(dbImage.get().getContentType())
                .imageData(dbImage.get().getImageData()).build();
    }

    public byte[] getImage(String name) {
        Optional<ImageData> dbImage = imageRepository.findByFileName(name);
        return dbImage.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "File Not Found!")).getImageData();
    }


    public List<ImageData> getAllFiles() {
        return imageRepository.findAll().stream()
                .distinct()
                .map(dbFile -> {
                    try {
                        return getImageData((MultipartFile) dbFile);
                    } catch (IOException | NoSuchAlgorithmException e) {
                        throw new RuntimeException(e);
                    }
                }).collect(Collectors.toList());
    }


    private String getCR32Checksum(byte[] bytes) throws NoSuchAlgorithmException {
        byte[] hash = MessageDigest.getInstance("MD5").digest(bytes);
        return new BigInteger(1, hash).toString(16);
    }

    private ImageData getImageData(MultipartFile file) throws IOException, NoSuchAlgorithmException {
        final var fileName = getFileName(file.getOriginalFilename());
        return ImageData.builder()
                .checksum(getCR32Checksum(file.getBytes()))
                .contentType(file.getContentType())
                .fileName(fileName)
                .filePath(storageProperties.getFilePath() + fileName)
                .imageResolution(new ImageResolution())
                .owner("seedon")
                .isPrivate(false)
                .size((file.getSize()))
                .url(storageProperties.getBaseUrl() + fileName)
                .imageData(file.getBytes())
                .build();
    }

    private String getFileName(String fileName) {
        int random = new Random().nextInt(999999);
        return random + "-" + StringUtils.cleanPath(Objects.requireNonNull(fileName));
    }

}