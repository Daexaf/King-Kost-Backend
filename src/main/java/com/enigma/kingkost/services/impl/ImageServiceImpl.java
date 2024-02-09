package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.entities.Image;
import com.enigma.kingkost.repositories.ImageRepository;
import com.enigma.kingkost.services.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    private final ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public List<Image> getImageByKostId(String kostId) {
        List<Image> imageList = imageRepository.findByKost_IdAndIsActiveTrue(kostId);
        if (imageList == null) {
            throw new NotFoundException("Image kost with kost id " + kostId + " not found");
        }
        return imageList;
    }

    @Override
    public List<Image> updateImage(Image image) {
        List<Image> imageList = getImageByKostId(image.getKost().getId());
        imageList.forEach((prevImage -> {
            if (!image.getFileName().equals(prevImage.getFileName())) {
                save(image);
                save(Image.builder()
                        .id(prevImage.getId())
                        .fileName(prevImage.getFileName())
                        .kost(prevImage.getKost())
                        .isActive(false)
                        .createdAt(prevImage.getCreatedAt())
                        .updatedAt(prevImage.getUpdatedAt())
                        .build());
            }
        }));
        return getImageByKostId(image.getKost().getId());
    }

    @Override
    public List<Image> getAllImage() {
        return imageRepository.findAll();
    }

    @Override
    public void deleteImage(Image image) {
        save(save(Image.builder()
                .id(image.getId())
                .fileName(image.getFileName())
                .kost(image.getKost())
                .isActive(false)
                .createdAt(image.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build()));
    }
}
