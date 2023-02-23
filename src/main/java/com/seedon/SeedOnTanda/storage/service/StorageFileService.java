package com.seedon.SeedOnTanda.storage.service;

import com.seedon.SeedOnTanda.storage.entity.StorageFiles;
import com.seedon.SeedOnTanda.storage.repository.StorageFilesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;
import java.util.zip.CRC32;
import java.util.zip.Checksum;

@Service
@RequiredArgsConstructor
public class StorageFileService {
    private final StorageFilesRepository storageFilesRepository;

    public StorageFiles store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        StorageFiles storageFiles = new StorageFiles(fileName, file.getContentType(), getCR32Checksum(file.getBytes()), file.getBytes());
        return storageFilesRepository.save(storageFiles);
    }

    public StorageFiles getFile(String id) {
        return storageFilesRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "File was not found"));
    }

    public Stream<StorageFiles> getAllFiles() {
        return storageFilesRepository.findAll().stream();
    }

    private long getCR32Checksum(byte[] bytes) {
        Checksum crc32 = new CRC32();
        crc32.update(bytes, 0, bytes.length);
        return crc32.getValue();
    }
}
