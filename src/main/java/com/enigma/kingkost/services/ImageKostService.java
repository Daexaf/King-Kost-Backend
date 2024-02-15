package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.response.ImageResponse;
import com.enigma.kingkost.entities.Image;
import com.enigma.kingkost.entities.Kost;

import java.util.List;

public interface ImageKostService {
    Image save(Image image);
    List<ImageResponse> getImageByKostId(String kostId);
    List<ImageResponse> updateImage(Image image);
    void deleteImage(ImageResponse imageResponse, Kost kost);
    List<Image> getByKostId(String id);
    Image getImageById(String id);
}
