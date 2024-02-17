package com.enigma.kingkost.mapper;

import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import com.enigma.kingkost.entities.Province;
import java.util.ArrayList;
import java.util.List;

public class ProvinceMapper {
    public static List<ProvinceResponse> listProvinceToListProvinceResponse(List<Province> provinces) {
        List<ProvinceResponse> provinceReponse = new ArrayList<>();
        provinces.forEach(province -> provinceReponse.add(ProvinceResponse.builder()
                .id(province.getId())
                .name(province.getName())
                .build()));
        return provinceReponse;
    }

    public static ProvinceResponse provinceToProvinceResponse(Province province) {
        return ProvinceResponse.builder()
                .id(province.getId())
                .name(province.getName())
                .build();
    }
}
