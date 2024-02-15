package com.enigma.kingkost.mapper;

import com.enigma.kingkost.dto.response.ImageResponse;
import com.enigma.kingkost.entities.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageMapper {
    public static List<ImageResponse> listImageToListImageResponse(List<Image> imageList) {
        List<ImageResponse> imageResponseList = new ArrayList<>();
        imageList.forEach(image -> {
            imageResponseList.add(ImageResponse.builder()
                    .id(image.getId())
                    .url(image.getUrl())
                    .isActive(image.getIsActive())
                    .kost_id(image.getKost().getId())
                    .build());
        });
        return imageResponseList;
    }
}
