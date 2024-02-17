package com.enigma.kingkost.mapper;

import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.entities.City;

import java.util.ArrayList;
import java.util.List;

public class CityMapper {
    public static List<CityResponse> listCityToListCityResponse(List<City> listCity ) {
     List<CityResponse> listCityResponse = new ArrayList<>();
     listCity.forEach(city -> listCityResponse.add(CityResponse.builder()
                     .id(city.getId())
                     .name(city.getName())
                     .province(city.getProvince())
             .build()));
     return listCityResponse;
    }

    public static CityResponse cityToCityResponse(City city) {
        return CityResponse.builder()
                .id(city.getId())
                .name(city.getName())
                .province(city.getProvince())
                .build();
    }
}
