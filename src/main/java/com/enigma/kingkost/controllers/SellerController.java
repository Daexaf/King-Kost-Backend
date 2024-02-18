package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.SellerRequest;
import com.enigma.kingkost.dto.response.CommondResponse;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.SELLER)
public class SellerController {
    private final SellerService sellerService;

    @GetMapping("/v1")
    public List<SellerResponse> getAllSeller() {
        return sellerService.getAll();
    }

    @GetMapping("/v1/{id}")
    public SellerResponse getSellById(@PathVariable String id) {
        return sellerService.getById(id);
    }

    @DeleteMapping("/v1/{id}")
    public void deleteSell(@PathVariable String id) {
        sellerService.deleteSeller(id);
    }

    @PutMapping("/v1")
    public SellerResponse updateSell(@RequestBody SellerRequest sellerRequest) {
        return sellerService.updateSeller(sellerRequest);
    }

    @PostMapping(value = "/v1/upload/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<SellerResponse> uploadSellerImage(@PathVariable String id, @RequestParam("file") MultipartFile file) {
        try {
            SellerResponse seller = sellerService.addOrUpdateProfileImageForSeller(id, file);
            if (seller != null) {
                return ResponseEntity.ok(seller);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping(AppPath.VALUE_GET_SELLER)
    public ResponseEntity<CommondResponse> getSellerByUserCredentialId(@PathVariable String id) {
        SellerResponse sellerResponse = sellerService.getSellerByUserCredentialId(id);
        return ResponseEntity.status(HttpStatus.OK).body(CommondResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .message("Success get customer")
                .data(sellerResponse)
                .build());
    }

}
