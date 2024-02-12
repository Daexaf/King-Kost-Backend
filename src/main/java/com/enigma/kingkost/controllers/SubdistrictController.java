package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.response.CommondResponse;
import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponse;
import com.enigma.kingkost.services.SubdistrictService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequestMapping(AppPath.VALUE_SUBDISTRICT)
@RequiredArgsConstructor
public class SubdistrictController {
    private final SubdistrictService subdistrictService;

    @GetMapping
    public ResponseEntity<CommondResponse> getSubdistrictBYCityId(@RequestParam String city_id) {
        List<SubdistrictResponse> subdistrictResponseList = subdistrictService.getByCityId(city_id);
        return ResponseEntity.status(HttpStatus.OK.value()).body(
                CommondResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Success get subdistrict by city id")
                        .data(subdistrictResponseList)
                        .build()
        );
    }
}