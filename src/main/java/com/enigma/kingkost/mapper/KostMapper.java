package com.enigma.kingkost.mapper;

import com.enigma.kingkost.dto.response.KostResponse;
import com.enigma.kingkost.entities.Image;
import com.enigma.kingkost.entities.Kost;
import com.enigma.kingkost.entities.KostPrice;

import java.util.List;

public class KostMapper {
    public static KostResponse kostToKostResponse(Kost kost, KostPrice kostPrice, List<Image> images) {
        return KostResponse.builder()
                .id(kost.getId())
                .name(kost.getName())
                .description(kost.getDescription())
                .kostPrice(kostPrice)
                .availableRoom(kost.getAvailableRoom())
                .isAc(kost.getIsAc())
                .isWifi(kost.getIsWifi())
                .isParking(kost.getIsParking())
                .genderType(kost.getGenderType())
                .seller(kost.getSeller())
                .images(images)
                .province(kost.getProvince())
                .city(kost.getCity())
                .subdistrict(kost.getSubdistrict())
                .createdAt(kost.getCreatedAt())
                .updatedAt(kost.getUpdatedAt())
                .build();
    }
}
