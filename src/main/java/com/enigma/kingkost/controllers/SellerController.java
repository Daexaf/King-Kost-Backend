package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.dto.request.SellerRequest;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "localhost:5173")
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.SELLER)
public class SellerController {
    private final SellerService sellerService;

    @PostMapping("/v1")
    public SellerResponse createSell(SellerRequest sellerRequest) {
        return sellerService.createSeller(sellerRequest);
    }

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
    public SellerResponse updateSell(SellerRequest sellerRequest) {
        return sellerService.updateSeller(sellerRequest);
    }

}
