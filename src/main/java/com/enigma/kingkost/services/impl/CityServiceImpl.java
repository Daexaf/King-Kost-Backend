package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.dto.rest.city.CityResponseDTO;
import com.enigma.kingkost.dto.rest.city.CityResponseRest;
import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import com.enigma.kingkost.entities.City;
import com.enigma.kingkost.entities.Province;
import com.enigma.kingkost.mapper.CityMapper;
import com.enigma.kingkost.repositories.CityRepository;
import com.enigma.kingkost.services.CityService;
import com.enigma.kingkost.services.ProvinceService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {
    private final RestTemplate restTemplate;
    private final CityRepository cityRepository;
    private final ProvinceService provinceService;
    @Value("${api.endpoint.url.city}")
    private String BASE_URL;


    @Override
    @PostConstruct
    public void saveCityToDatabase() {
        List<CityResponse> cityResponseListInDatabase = getAllCity();
        if (cityResponseListInDatabase.isEmpty()) {
            List<ProvinceResponse> listProvinces = provinceService.getAllProvince();
            listProvinces.forEach(province -> {
                String cityApiUrl = BASE_URL + province.getId();
                ResponseEntity<CityResponseDTO> responseEntity = restTemplate.getForEntity(cityApiUrl, CityResponseDTO.class);
                List<CityResponseRest> responseList = List.of(responseEntity.getBody().getValue());
                for (CityResponseRest cityResponseRest : responseList) {
                    System.out.println(cityResponseRest);
                    cityRepository.save(City.builder()
                            .id(cityResponseRest.getId())
                            .name(cityResponseRest.getName())
                            .province(Province.builder()
                                    .id(province.getId())
                                    .name(province.getName())
                                    .build())
                            .build());
                }
            });
        }
    }

    @Override
    public List<CityResponse> getAllCity() {
        List<City> listCity = cityRepository.findAll();
        return CityMapper.listCityToListCityResponse(listCity);
    }

    @Override
    public List<CityResponse> getByProvinceId(String id) {
        if (id.isEmpty()) {
            throw new NullPointerException("Province id is required");
        }
        List<City> cities = cityRepository.findByProvinceId(id);
        if (cities.isEmpty()) {
            throw new NotFoundException("City not found");
        }
        return CityMapper.listCityToListCityResponse(cities);
    }

    @Override
    public City getCityById(String id) {
        City city = cityRepository.findById(id).orElse(null);
        if (city == null) {
            throw new NotFoundException("City not found");
        }
        return city;
    }
}
