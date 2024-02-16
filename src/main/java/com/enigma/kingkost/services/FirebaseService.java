package com.enigma.kingkost.services;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FirebaseService {

    String uploadFile(MultipartFile file) throws IOException;

}
