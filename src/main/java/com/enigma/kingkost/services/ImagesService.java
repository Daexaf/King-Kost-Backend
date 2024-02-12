package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.entities.Images;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

@Service
public interface ImagesService {

    Images store(MultipartFile file) throws IOException;

    Images getImages(String id);

    Stream<Images> getAllImages();

//    CustomerResponse addOrUpdateProfileImageForCustomer(String customerId, MultipartFile profileImage) throws IOException;
//
//    SellerResponse addOrUpdateProfileImageForSeller(String sellerId, MultipartFile profileImage) throws IOException;

}
