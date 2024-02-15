package com.enigma.kingkost.services.impl;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class FileStorageServiceImpl implements com.enigma.kingkost.services.FileStorageService {
    @Value("${app.kingkost.bucket.firebase}")
    private String BUCKET_URL;

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        String fileName = UUID.randomUUID() + getExtention(file.getOriginalFilename());
        BlobId blobId = BlobId.of(BUCKET_URL, fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("firebase-adminsdk-king-kost-forkost.json");
        GoogleCredentials googleCredentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(googleCredentials).build().getService();
        storage.create(blobInfo, file.getBytes());
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/" + BUCKET_URL + "/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, fileName);
    }

    @Override
    public String getExtention(String fileName) {
        return Path.of(fileName).getFileName().toString();
    }

}
