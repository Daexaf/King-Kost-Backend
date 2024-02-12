package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.services.impl.FileStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequestMapping(AppPath.VALUE_GET_IMAGE)
@RequiredArgsConstructor
public class ImageController {
    private final FileStorageServiceImpl fileStorageServiceImpl;

    @GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
        byte[] imgBytes = fileStorageServiceImpl.getImageStatic(imageName);
        if (imgBytes == null) {
            return ResponseEntity.notFound().build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imgBytes, headers, HttpStatus.OK);
    }
}
