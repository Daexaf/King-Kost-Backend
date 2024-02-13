package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.ImagesProfile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public interface ImagesProfileService {

    ImagesProfile store(MultipartFile file) throws IOException;

    ImagesProfile getImages(String id);

    Stream<ImagesProfile> getAllImages();




}
