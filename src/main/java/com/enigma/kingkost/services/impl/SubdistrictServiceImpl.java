package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponse;
import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponseDTO;
import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponseRest;
import com.enigma.kingkost.entities.City;
import com.enigma.kingkost.entities.Subdistrict;
import com.enigma.kingkost.mapper.SubdistrictMapper;
import com.enigma.kingkost.repositories.SubdistrictRepository;
import com.enigma.kingkost.services.CityService;
import com.enigma.kingkost.services.SubdistrictService;
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
public class SubdistrictServiceImpl implements SubdistrictService {
    private final CityService cityService;
    private final SubdistrictRepository subdistrictRepository;
    private final RestTemplate restTemplate;
    @Value("${api.endpoint.url.subdistrict}")
    private String URL_SUBDISTRICT;

    @Override
    @PostConstruct
    public void saveToDatabase() {
        List<SubdistrictResponse> subdistrictResponseList = getAll();

        if (subdistrictResponseList.isEmpty()) {
            List<CityResponse> cityResponseList = cityService.getAllCity();
            cityResponseList.forEach(cityResponse -> {
                ResponseEntity<SubdistrictResponseDTO> responseEntity = restTemplate.getForEntity(URL_SUBDISTRICT + cityResponse.getId(), SubdistrictResponseDTO.class);
                List<SubdistrictResponseRest> subdistrictResponseRests = List.of(responseEntity.getBody().getValue());
                subdistrictResponseRests.forEach(subdistrictResponseRest ->
                        subdistrictRepository.save(Subdistrict.builder()
                                .id(subdistrictResponseRest.getId())
                                .name(subdistrictResponseRest.getName())
                                .city(City.builder()
                                        .id(cityResponse.getId())
                                        .name(cityResponse.getName())
                                        .province(cityResponse.getProvince())
                                        .build())
                                .build())
                );
            });
        }

    }

    @Override
    public List<SubdistrictResponse> getByCityId(String id) {
        if (id.isEmpty()) {
            throw new NullPointerException("City id is required");
        }
        List<Subdistrict> subdistricts = subdistrictRepository.findByCity_Id(id);
        if (subdistricts.isEmpty()) {
            throw new NotFoundException("Subdistrict not found");
        }
        return SubdistrictMapper.subdistrictListToSubdistrictResponse(subdistricts);
    }

    @Override
    public List<SubdistrictResponse> getAll() {
        List<Subdistrict> subdistrictList = subdistrictRepository.findAll();
        return SubdistrictMapper.subdistrictListToSubdistrictResponse(subdistrictList);
    }

    @Override
    public Subdistrict getSubdistrictById(String id) {
        Subdistrict subdistrict = subdistrictRepository.findById(id).orElse(null);
        if (subdistrict == null) {
            throw new NotFoundException("Subdistrict not found");
        }
        return subdistrict;
    }
}
