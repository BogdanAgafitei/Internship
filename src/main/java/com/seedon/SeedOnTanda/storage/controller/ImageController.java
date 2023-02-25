package com.seedon.SeedOnTanda.storage.controller;

import com.seedon.SeedOnTanda.storage.entity.ImageData;
import com.seedon.SeedOnTanda.storage.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequestMapping("/v1/files")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException, NoSuchAlgorithmException {
        return ResponseEntity.status(HttpStatus.OK)
                .body(imageService.store(file));
    }

    @GetMapping
    public ResponseEntity<List<ImageData>> getListFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(imageService.getAllFiles());
    }

    @GetMapping("/{name}")
    public ResponseEntity<byte[]> getFile(@PathVariable String name) {
        byte[] image = imageService.getImage(name);

        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/png"))
                .body(image);
    }
}


