package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.Image;

import java.util.List;

public interface ImageService {
    Image save(Image image);
    List<Image> getImageByKostId(String kostId);
    List<Image> updateImage(Image image);
    List<Image> getAllImage();
    void deleteImage(Image image);
}
