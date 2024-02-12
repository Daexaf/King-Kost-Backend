package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.entities.Images;
import com.enigma.kingkost.services.ImagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@RequestMapping(AppPath.IMAGE)
public class ImagesController {

    private final ImagesService imagesService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Images> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            Images savedImage = imagesService.store(file);
            String name = savedImage.getName() != null ? savedImage.getName() : "Unknown";
            String type = savedImage.getType() != null ? savedImage.getType() : "Unknown";
            String data = savedImage.getData() != null ? Arrays.toString(savedImage.getData()) : "data";

            return ResponseEntity.ok(Images.builder()
                    .id(savedImage.getId())
                    .name(name)
                    .type(type)
                    .data(data.getBytes())
                    .build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(@PathVariable String id) {
        Images image = imagesService.getImages(id);
        if (image != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + image.getName() + "\"")
                    .body(image.getData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Images>> getAllImages() {
        List<Images> images = imagesService.getAllImages().collect(Collectors.toList());
        return ResponseEntity.ok(images);
    }
}
