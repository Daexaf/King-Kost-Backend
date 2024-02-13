package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.services.FirebaseService;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FirebaseServiceImpl implements FirebaseService {

    @Override
    public String uploadFile(MultipartFile file) throws IOException {

        String fileName = UUID.randomUUID().toString() + getExtension(file.getOriginalFilename());
        BlobId blobId = BlobId.of("kingkost-images.appspot.com", fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("kingkost-images-firebase-adminsdk-17fqf-e8d6a054a3.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(inputStream);
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, file.getBytes());
        String DOWNLOAD_URL = "https://firebasestorage.googleapis.com/v0/b/kingkost-images.appspot.com/o/%s?alt=media";
        return String.format(DOWNLOAD_URL, fileName);
    }

    private String getExtension(String fileName) {
        return Path.of(fileName).getFileName().toString();
    }
}
