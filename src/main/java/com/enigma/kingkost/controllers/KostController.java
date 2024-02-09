package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.KostRequest;
import com.enigma.kingkost.dto.response.CommondResponse;
import com.enigma.kingkost.dto.response.KostResponse;
import com.enigma.kingkost.entities.Image;
import com.enigma.kingkost.services.FileStorageService;
import com.enigma.kingkost.services.KostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.VALUE_KOST)
public class KostController {
    private final KostService kostService;
    private final FileStorageService fileStorageService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommondResponse> createKost(@RequestParam("name") String name, @RequestParam("description") String description, @RequestParam("price") Double price, @RequestParam("availableRoom") Integer availableRoom, @RequestParam("image") MultipartFile[] image, @RequestParam("seller_id") String sellerId, @RequestParam("isWifi") Boolean isWifi, @RequestParam("isAc") Boolean isAc, @RequestParam("isParking") Boolean isParking, @RequestParam("genderId") String genderId, @RequestParam("provinceId") String provinceId, @RequestParam("cityId") String cityId, @RequestParam("subdistrictId") String subdistrictId) {
        KostResponse kostResponse = kostService.createKostAndKostprice(KostRequest.builder()
                .name(name)
                .description(description)
                .availableRoom(availableRoom)
                .image(image)
                .sellerId(sellerId)
                .isWifi(isWifi)
                .isAc(isAc)
                .isParking(isParking)
                .price(price)
                .genderId(genderId)
                .provinceId(provinceId)
                .cityId(cityId)
                .subdistrictId(subdistrictId)
                .build());

        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success save kost")
                .data(kostResponse)
                .build());
    }


}
