package com.enigma.kingkost.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String uploadFile(MultipartFile file) throws IOException;
    String getExtention(String fileName);
}
