package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.SellerRequest;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.entities.GenderType;
import com.enigma.kingkost.entities.Seller;
import com.enigma.kingkost.repositories.SellerRepository;
import com.enigma.kingkost.services.GenderService;
import com.enigma.kingkost.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final GenderService genderService;

    @Override
    public SellerResponse createSeller(SellerRequest sellerRequest) {
        GenderType gender = sellerRequest.getGenderTypeId();
        Seller seller = Seller.builder()
                .id(sellerRequest.getId())
                .fullName(sellerRequest.getFullName())
                .email(sellerRequest.getEmail())
                .phoneNumber(sellerRequest.getPhoneNumber())
                .address(sellerRequest.getAddress())
                .genderTypeId(gender)
                .userCredentialId(sellerRequest.getUserCredentialId())
                .build();
        sellerRepository.save(seller);
        return getSellerResponse(seller);
    }

    @Override
    public SellerResponse updateSeller(SellerRequest sellerRequest) {
        Seller currentSellerId = sellerRepository.findById(sellerRequest.getId()).orElse(null);
        if (currentSellerId != null) {
            GenderType gender = genderService.getById(sellerRequest.getGenderTypeId().getId()); // Ambil ID gender yang sesuai
            Seller seller = Seller.builder()
                    .id(sellerRequest.getId())
                    .fullName(sellerRequest.getFullName())
                    .email(sellerRequest.getEmail())
                    .phoneNumber(sellerRequest.getPhoneNumber())
                    .address(sellerRequest.getAddress())
                    .genderTypeId(gender)
                    .build();
            sellerRepository.save(seller);
            return getSellerResponse(seller);
        }
        return null;
    }


    @Override
    public void deleteSeller(String id) {
        sellerRepository.deleteById(id);
    }

    @Override
    public List<SellerResponse> getAll() {
        List<Seller> sellers = sellerRepository.findAll();
        return sellers.stream().map(this::getSellerResponse).collect(Collectors.toList());
    }

    @Override
    public SellerResponse getById(String id) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        assert seller != null;
        return getSellerResponse(seller);
    }

    private SellerResponse getSellerResponse(Seller seller) {
        GenderType gender =  seller.getGenderTypeId();
        return SellerResponse.builder()
                .id(seller.getId())
                .fullName(seller.getFullName())
                .address(seller.getAddress())
                .email(seller.getEmail())
                .phoneNumber(seller.getPhoneNumber())
                .genderTypeId(gender.getId())
                .build();
    }
}
