package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.services.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.FIREBASE)
public class FirebaseController {

    private final FirebaseService firebaseService;

    @PostMapping("/api/images/upload")
    public String uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String imageUrl = firebaseService.uploadFile(file);
            return "Image uploaded successfully. URL: " + imageUrl;
        } catch (IOException e) {
            e.printStackTrace();
            return "Image upload failed. Something went wrong.";
        }
    }
}
