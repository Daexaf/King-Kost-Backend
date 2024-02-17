package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.SellerRequest;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.entities.Seller;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public interface SellerService {
    SellerResponse createSeller(Seller seller);

    SellerResponse updateSeller(SellerRequest sellerRequest);

    void deleteSeller(String id);

    List<SellerResponse> getAll();

    SellerResponse getById(String id);

    Seller getSellerById(String id);

    SellerResponse addOrUpdateProfileImageForSeller(String sellerId, MultipartFile profileImage) throws IOException;

    SellerResponse getSellerByUserCredentialId(String userCredentialId);
}
