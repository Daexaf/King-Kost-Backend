package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.response.CommondResponse;
import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.services.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequestMapping(AppPath.VALUE_CITY)
@RequiredArgsConstructor
public class CityController {
    private final CityService cityService;

    @GetMapping
    public ResponseEntity<CommondResponse> getCityByProvinceId(@RequestParam String province_id) {
        List<CityResponse> cityResponseList = cityService.getByProvinceId(province_id);
        return ResponseEntity.status(HttpStatus.OK).body(
                CommondResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success get city by province")
                        .data(cityResponseList)
                        .build()
        );
    }
}
