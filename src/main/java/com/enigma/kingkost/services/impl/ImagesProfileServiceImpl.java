package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.entities.ImagesProfile;
import com.enigma.kingkost.repositories.ImagesProfileRepository;
import com.enigma.kingkost.services.FirebaseService;
import com.enigma.kingkost.services.ImagesProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ImagesProfileServiceImpl implements ImagesProfileService {

    private final ImagesProfileRepository imagesProfileRepository;
    private final FirebaseService firebaseService;

    @Override
    public ImagesProfile store(MultipartFile file) throws IOException {
        String imageUrl = firebaseService.uploadFile(file);
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

        ImagesProfile image = new ImagesProfile();
        image.setName(fileName);
        image.setType(file.getContentType());
        image.setUrl(imageUrl);
        return imagesProfileRepository.save(image);
    }

    @Override
    public ImagesProfile getImages(String id) {
        return imagesProfileRepository.findById(id).orElse(null);
    }

    @Override
    public Stream<ImagesProfile> getAllImages() {
        return imagesProfileRepository.findAll().stream();
    }

}
