package com.enigma.kingkost.mapper;

import com.enigma.kingkost.dto.response.KostResponse;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponse;
import com.enigma.kingkost.entities.*;

import java.util.List;

public class KostMapper {
    public static KostResponse kostToKostResponse(Kost kost, KostPrice kostPrice, List<Image> images, Integer currentBookingStatus) {
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
                .seller(SellerResponse.builder()
                        .id(kost.getSeller().getId())
                        .fullName(kost.getSeller().getFullName())
                        .email(kost.getSeller().getEmail())
                        .phoneNumber(kost.getSeller().getPhoneNumber())
                        .address(kost.getSeller().getAddress())
                        .genderTypeId(kost.getSeller().getGenderTypeId())
                        .username(kost.getSeller().getUserCredential().getUsername())
                        .build())
                .images(ImageMapper.listImageToListImageResponse(images))
                .province(ProvinceResponse.builder()
                        .id(kost.getProvince().getId())
                        .name(kost.getProvince().getName())
                        .build())
                .city(CityResponse.builder()
                        .id(kost.getCity().getId())
                        .name(kost.getCity().getName())
                        .province(kost.getProvince())
                        .build())
                .subdistrict(SubdistrictResponse.builder()
                        .id(kost.getSubdistrict().getId())
                        .name(kost.getSubdistrict().getName())
                        .city(kost.getCity())
                        .build())
                .currentBookingStatus(currentBookingStatus)
                .isActive(kost.getIsActive())
                .createdAt(kost.getCreatedAt())
                .updatedAt(kost.getUpdatedAt())
                .build();
    }

}
