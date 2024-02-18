package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.rest.province.ProvinceResponseDTO;
import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import com.enigma.kingkost.dto.rest.province.ProvinceResponseRest;
import com.enigma.kingkost.entities.Province;
import com.enigma.kingkost.mapper.ProvinceMapper;
import com.enigma.kingkost.repositories.ProvinceRepository;
import com.enigma.kingkost.services.ProvinceService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceServiceImpl implements ProvinceService {

    private final RestTemplate restTemplate;
    private final ProvinceRepository provinceRepository;
    @Value("${api.endpoint.url.province}")
    private String BASE_URL;

    @Override
    @PostConstruct
    public void saveProvinceToDatabase() {
        List<ProvinceResponse> listProvinceInDatabase = getAllProvince();
        if (listProvinceInDatabase.isEmpty()) {
            ResponseEntity<ProvinceResponseDTO> responseEntity = restTemplate.getForEntity(BASE_URL, ProvinceResponseDTO.class);
            List<ProvinceResponseRest> listProvinces = List.of(responseEntity.getBody().getValue());
            if (!listProvinces.isEmpty()) {
                for (ProvinceResponseRest provinceResponse : listProvinces) {
                    provinceRepository.save(Province.builder()
                            .id(provinceResponse.getId())
                            .name(provinceResponse.getName())
                            .build());
                }
            } else {
                throw new NotFoundException("Province get is empty");
            }
        }
    }

    @Override
    public List<ProvinceResponse> getAllProvince() {
        List<Province> provinces = provinceRepository.findAll();
        return ProvinceMapper.listProvinceToListProvinceResponse(provinces);
    }

    @Override
    public ProvinceResponse getProvinceById(String id) {
        Province province = provinceRepository.findById(id).orElse(null);
        if (province == null) {
            throw new NotFoundException("Province not found");
        }
        return ProvinceMapper.provinceToProvinceResponse(province);
    }
}
