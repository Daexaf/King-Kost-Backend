package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import com.enigma.kingkost.dto.response.CommondResponse;
import com.enigma.kingkost.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequestMapping(AppPath.VALUE_PROVINCE)
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;

    @GetMapping
    public ResponseEntity<CommondResponse> getAllProvinces() {
        List<ProvinceResponse> responseList = provinceService.getAllProvince();
        return ResponseEntity.status(HttpStatus.OK).body(
                CommondResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success get all province")
                        .data(responseList)
                        .build()
        );
    }
}
