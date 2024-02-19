package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.SellerRequest;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.entities.*;
import com.enigma.kingkost.repositories.SellerRepository;
import com.enigma.kingkost.services.GenderService;
import com.enigma.kingkost.services.ImagesProfileService;
import com.enigma.kingkost.services.SellerService;
import com.enigma.kingkost.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;
    private final GenderService genderService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final ImagesProfileService imagesProfileService;

    @Override
    public SellerResponse createSeller(Seller sellerRequest) {
        Seller seller = sellerRepository.save(sellerRequest);
        SellerResponse sellerResponse = SellerResponse.builder()
                .id(seller.getId())
                .address(seller.getAddress())
                .phoneNumber(seller.getPhoneNumber())
                .email(seller.getEmail())
                .fullName(seller.getFullName())
                .genderTypeId(seller.getGenderTypeId())
                .build();
        return sellerResponse;
    }

    @Override
    public SellerResponse updateSeller(SellerRequest sellerRequest) {
        Seller currentSellerId = sellerRepository.findById(sellerRequest.getId()).orElse(null);

        if (currentSellerId != null) {
            UserCredential userCredential = currentSellerId.getUserCredential();
            if (userCredential == null) {
                userCredential = new UserCredential();
            }
            userCredential.setUsername(sellerRequest.getUsername());
            userCredential.setPassword(passwordEncoder.encode(sellerRequest.getPassword()));
            userService.updateUserCredential(userCredential);
            GenderType gender = genderService.getById(sellerRequest.getGenderTypeId());
            currentSellerId.setFullName(sellerRequest.getFullName());
            currentSellerId.setAddress(sellerRequest.getAddress());
            currentSellerId.setEmail(sellerRequest.getEmail());
            currentSellerId.setPhoneNumber(sellerRequest.getPhoneNumber());
            currentSellerId.setGenderTypeId(gender);
            currentSellerId.setUserCredential(userCredential);
            currentSellerId.setActive(sellerRequest.isActive());
            sellerRepository.save(currentSellerId);

            return getSellerResponse(currentSellerId);
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

    @Override
    public Seller getSellerById(String id) {
        Seller seller = sellerRepository.findById(id).orElse(null);
        if (seller == null) {
            throw new NullPointerException("Seller not found");
        }
        return seller;
    }

    @Override
    public SellerResponse addOrUpdateProfileImageForSeller(String sellerId, MultipartFile profileImage) throws IOException {
        ImagesProfile imagesProfile = imagesProfileService.store(profileImage);
        Seller seller = sellerRepository.findById(sellerId).orElse(null);

        if(seller != null){
            seller.setProfileImageName(imagesProfile.getName());
            seller.setProfileImageType(imagesProfile.getType());
            seller.setUrl(imagesProfile.getUrl());

            Seller updatedSeller = sellerRepository.save(seller);

            return convertToResponse(updatedSeller);
        }

        return null;
    }

    @Override
    public SellerResponse getSellerByUserCredentialId(String userCredentialId) {
        Seller findSeller = sellerRepository.findByUserCredentialId(userCredentialId).orElse(null);
        if (findSeller == null) {
            throw new NullPointerException("Seller not found");
        }
        return SellerResponse.builder()
                .id(findSeller.getId())
                .address(findSeller.getAddress())
                .email(findSeller.getEmail())
                .username(findSeller.getUserCredential().getUsername())
                .phoneNumber(findSeller.getPhoneNumber())
                .genderTypeId(findSeller.getGenderTypeId())
                .url(findSeller.getUrl())
                .profileImageType(findSeller.getProfileImageType())
                .profileImageName(findSeller.getProfileImageName())
                .fullName(findSeller.getFullName())
                .active(findSeller.isActive())
                .build();
    }

    private SellerResponse convertToResponse(Seller seller) {
        return SellerResponse.builder()
                .id(seller.getId())
                .fullName(seller.getFullName())
                .email(seller.getEmail())
                .genderTypeId(seller.getGenderTypeId())
                .phoneNumber(seller.getPhoneNumber())
                .address(seller.getAddress())
                .profileImageName(seller.getProfileImageName())
                .profileImageType(seller.getProfileImageType())
                .url(seller.getUrl())
                .active(seller.isActive())
                .username(seller.getUserCredential().getUsername())
                .build();
    }

    private SellerResponse getSellerResponse(Seller seller) {
        return SellerResponse.builder()
                .id(seller.getId())
                .fullName(seller.getFullName())
                .address(seller.getAddress())
                .email(seller.getEmail())
                .phoneNumber(seller.getPhoneNumber())
                .genderTypeId(seller.getGenderTypeId())
                .profileImageType(seller.getProfileImageType())
                .profileImageName(seller.getProfileImageName())
                .url(seller.getUrl())
                .username(seller.getUserCredential().getUsername())
                .active(seller.isActive())
                .build();
    }


}
