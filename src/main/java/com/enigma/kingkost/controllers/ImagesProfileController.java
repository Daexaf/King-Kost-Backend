package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.entities.ImagesProfile;
import com.enigma.kingkost.services.ImagesProfileService;
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
@CrossOrigin(origins = AppPath.URL_CROSS)
@RequiredArgsConstructor
@RequestMapping(AppPath.IMAGE)
public class ImagesProfileController {

    private final ImagesProfileService imagesProfileService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ImagesProfile> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            ImagesProfile savedImage = imagesProfileService.store(file);
            String name = savedImage.getName() != null ? savedImage.getName() : "Unknown";
            String type = savedImage.getType() != null ? savedImage.getType() : "Unknown";
            String url = savedImage.getUrl() != null ? savedImage.getUrl() : "Unknown";

            return ResponseEntity.ok(ImagesProfile.builder()
                    .id(savedImage.getId())
                    .name(name)
                    .type(type)
                    .url(url)
                    .build());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getImage(@PathVariable String id) {
        ImagesProfile image = imagesProfileService.getImages(id);
        if (image != null) {
            return ResponseEntity.ok(image.getUrl());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<ImagesProfile>> getAllImages() {
        List<ImagesProfile> images = imagesProfileService.getAllImages().collect(Collectors.toList());
        return ResponseEntity.ok(images);
    }

}
