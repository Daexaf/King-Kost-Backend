package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.response.CommondResponse;
import com.enigma.kingkost.services.impl.FileStorageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequestMapping(AppPath.VALUE_GET_IMAGE)
@RequiredArgsConstructor
public class ImageController {
    private final FileStorageServiceImpl fileStorageServiceImpl;

    @PostMapping
    public ResponseEntity<CommondResponse> saveImage(@RequestParam("file") MultipartFile file) throws IOException {
        String fileUrl = fileStorageServiceImpl.uploadFile(file);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success save image")
                .data(fileUrl)
                .build());
    }

}
