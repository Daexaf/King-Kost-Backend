package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.entities.City;

import java.util.List;

public interface CityService {
    void saveCityToDatabase();

    List<CityResponse> getAllCity();

    List<CityResponse> getByProvinceId(String id);
    City getCityById(String id);
}
