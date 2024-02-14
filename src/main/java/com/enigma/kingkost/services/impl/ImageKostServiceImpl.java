package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.response.ImageResponse;
import com.enigma.kingkost.entities.Image;
import com.enigma.kingkost.entities.Kost;
import com.enigma.kingkost.mapper.ImageMapper;
import com.enigma.kingkost.repositories.ImageRepository;
import com.enigma.kingkost.services.ImageKostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageKostServiceImpl implements ImageKostService {
    private final ImageRepository imageRepository;

    @Override
    public Image save(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public List<ImageResponse> getImageByKostId(String kostId) {
        List<Image> imageList = imageRepository.findByKost_IdAndIsActiveTrue(kostId);
        if (imageList == null) {
            throw new NotFoundException("Image kost with kost id " + kostId + " not found");
        }
        return ImageMapper.listImageToListImageResponse(imageList);
    }

    @Override
    public List<ImageResponse> updateImage(Image image) {
        List<Image> imageList = getByKostId(image.getKost().getId());
        imageList.forEach((prevImage -> {
            if (!image.getUrl().equals(prevImage.getUrl())) {
                save(image);
                save(Image.builder()
                        .id(prevImage.getId())
                        .url(prevImage.getUrl())
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
    public void deleteImage(ImageResponse imageResponse, Kost kost) {
        Image image = getImageById(imageResponse.getId());
        Image prevImage = Image.builder()
                .id(imageResponse.getId())
                .url(imageResponse.getUrl())
                .kost(kost)
                .isActive(false)
                .createdAt(image.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();
        save(prevImage);
    }

    @Override
    public List<Image> getByKostId(String id) {
        List<Image> images = imageRepository.findByKost_IdAndIsActiveTrue(id);
        if (images == null) {
            throw new NotFoundException("Image with kost id " + id + " not found");
        }
        return images;
    }

    @Override
    public Image getImageById(String id) {
        Image image = imageRepository.findById(id).orElse(null);
        if (image == null) {
            throw new NotFoundException("Image with id " + id + "not found");
        }
        return image;
    }
}
