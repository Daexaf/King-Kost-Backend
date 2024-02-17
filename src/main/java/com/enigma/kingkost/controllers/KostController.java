package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.GetAllRequest;
import com.enigma.kingkost.dto.request.KostRequest;
import com.enigma.kingkost.dto.request.UpdateImageKostRequest;
import com.enigma.kingkost.dto.request.UpdateKostRequest;
import com.enigma.kingkost.dto.response.*;
import com.enigma.kingkost.services.KostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.VALUE_KOST)
public class KostController {
    private final KostService kostService;

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

    @GetMapping
    public ResponseEntity<CommondResponseWithPagging> getAllKostAndManyFeature(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "maxPrice", required = false) Double maxPrice, @RequestParam(name = "province_id", required = false) String province_id, @RequestParam(name = "city_id", required = false) String city_id, @RequestParam(name = "subdistrict_id", required = false) String subdistrict_id, @RequestParam(value = "gender_type_id", required = false) String gender_type_id, @RequestParam(value = "page", required = false, defaultValue = "0") Integer page, @RequestParam(value = "size", required = false, defaultValue = "5") Integer size, @RequestParam(value = "sellerId") String sellerId) {
        Page<KostResponse> kostResponses = kostService.getAll(GetAllRequest.builder()
                .name(name)
                .maxPrice(maxPrice)
                .province_id(province_id)
                .city_id(city_id)
                .subdistrict_id(subdistrict_id)
                .gender_type_id(gender_type_id)
                .sellerId(sellerId)
                .page(page)
                .size(size)
                .build());
        PaggingResponse paggingResponse = PaggingResponse.builder()
                .currentPage(page)
                .size(size)
                .totalPage(kostResponses.getTotalPages())
                .build();

        return ResponseEntity.status(HttpStatus.OK).body(CommondResponseWithPagging.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success")
                .data(kostResponses.getContent())
                .paggingResponse(paggingResponse)
                .build());
    }

    @PutMapping
    public ResponseEntity<CommondResponse> updateKost(@Valid @RequestBody UpdateKostRequest updateKostRequest) {

        KostResponse kostResponse = kostService.updateKost(updateKostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success update kost")
                .data(kostResponse)
                .build());
    }

    @GetMapping(AppPath.VALUE_ID)
    public ResponseEntity<CommondResponse> getKostById(@PathVariable String id) {
        KostResponse kostResponse = kostService.getByIdKost(id);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success get kost")
                .data(kostResponse)
                .build());
    }

    @DeleteMapping(AppPath.VALUE_ID)
    public ResponseEntity<CommondResponse> deleteKost(@PathVariable String id) {
        kostService.deleteKost(id);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success delete kost with id " + id)
                .build());
    }

    @PostMapping(value = AppPath.VALUE_IMAGE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CommondResponse> updateImageKost(@RequestParam("kostId") String kostId, @RequestParam("fileImages") MultipartFile[] fileImages) {
        UpdateImageKostRequest updateImageKostRequest = UpdateImageKostRequest.builder()
                .kost_id(kostId)
                .fileImages(fileImages)
                .build();
        kostService.updateImageKost(updateImageKostRequest);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success update image kost")
                .build());
    }

}
