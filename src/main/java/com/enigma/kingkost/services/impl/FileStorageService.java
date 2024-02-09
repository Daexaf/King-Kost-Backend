package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.entities.Image;
import com.enigma.kingkost.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;


import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileStorageService implements com.enigma.kingkost.services.FileStorageService {
    private final Path fileStorageLocation;
    private final ImageService imageService;
    private final String PATH_GET_IMAGE;


    public FileStorageService(@Value("${app.kingkost.path.saveimage}") String pathLocation, ImageService imageService, @Value("${app.kingkost.path.getimage}") String pathGetImage) {
        try {
            PATH_GET_IMAGE = pathGetImage;
            this.imageService = imageService;
            Files.createDirectories(this.fileStorageLocation = Paths.get(pathLocation));
        } catch (Exception e) {
            throw new RuntimeException("Could not create directory");
        }
    }


    @Override
    public String storageFile(MultipartFile file) {
        String mimeType = file.getContentType();
        if (mimeType == null || mimeType.startsWith("image/*")) {
            throw new RuntimeException("Invalid upload only image");
        }
        try {
            String[] arrString = file.getContentType().split("/");
            String contentType = arrString[0] + "-" + Math.random() + '-' + arrString[1];
            Path targetLocation = this.fileStorageLocation.resolve(contentType);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return contentType;
        } catch (IOException exception) {
            throw new RuntimeException("Could not store " + file.getOriginalFilename() + "please check again " + exception);
        }
    }

    @Override
    public String getFile(String fileName) {
        try {
            Path pathTarget = this.fileStorageLocation.resolve(fileName).normalize();
            return new UrlResource(pathTarget.toUri()).getFilename();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    public byte[] getImageStatic(String imageName) throws IOException {
        Resource resource = new ClassPathResource(PATH_GET_IMAGE + imageName);
        if (resource.exists()) {
            return Files.readAllBytes(resource.getFile().toPath());
        } else {
            return null;
        }
    }
}
