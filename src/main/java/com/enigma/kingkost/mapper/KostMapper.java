package com.enigma.kingkost.mapper;

import com.enigma.kingkost.dto.response.KostResponse;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponse;
import com.enigma.kingkost.entities.*;

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
                .seller(SellerResponse.builder()
                        .id(kost.getSeller().getId())
                        .fullName(kost.getSeller().getFullName())
                        .email(kost.getSeller().getEmail())
                        .phoneNumber(kost.getSeller().getPhoneNumber())
                        .address(kost.getSeller().getAddress())
                        .genderTypeId(kost.getSeller().getGenderTypeId())
                        .build())
                .images(ImageMapper.listImageToListImageResponse(images))
                .province(ProvinceResponse.builder()
                        .id(kost.getProvince().getId())
                        .name(kost.getProvince().getName())
                        .createdAt(kost.getProvince().getCreatedAt())
                        .build())
                .city(CityResponse.builder()
                        .id(kost.getCity().getId())
                        .name(kost.getCity().getName())
                        .province(kost.getProvince())
                        .build())
                .subdistrict(SubdistrictResponse.builder()
                        .id(kost.getSubdistrict().getId())
                        .name(kost.getSubdistrict().getId())
                        .city(kost.getCity())
                        .build())
                .createdAt(kost.getCreatedAt())
                .updatedAt(kost.getUpdatedAt())
                .build();
    }

    public static Kost kostResponseToKost(KostResponse kostResponse){
        return Kost.builder()
                .id(kostResponse.getId())
                .name(kostResponse.getName())
                .description(kostResponse.getDescription())
                .availableRoom(kostResponse.getAvailableRoom())
                .seller(Seller.builder()
                        .id(kostResponse.getSeller().getId())
                        .fullName(kostResponse.getSeller().getFullName())
                        .email(kostResponse.getSeller().getEmail())
                        .genderTypeId(kostResponse.getGenderType())
                        .phoneNumber(kostResponse.getSeller().getPhoneNumber())
                        .address(kostResponse.getSeller().getAddress())
                        .build())
                .isWifi(kostResponse.getIsWifi())
                .isAc(kostResponse.getIsAc())
                .isParking(kostResponse.getIsParking())
                .genderType(kostResponse.getGenderType())
                .province(Province.builder()
                        .id(kostResponse.getProvince().getId())
                        .build())
                .city(City.builder()
                        .id(kostResponse.getCity().getId())
                        .build())
                .subdistrict(Subdistrict.builder()
                        .id(kostResponse.getSubdistrict().getId())
                        .build())
                .isActive(kostResponse.getIsActive())
                .createdAt(kostResponse.getCreatedAt())
                .updatedAt(kostResponse.getUpdatedAt())
                .build();
    }
}
