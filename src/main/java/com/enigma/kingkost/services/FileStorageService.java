package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.Image;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileStorageService {
    String storageFile(MultipartFile multipartFile);
    String getFile(String fileName);
    byte[] getImageStatic(String imageName) throws IOException;
}
