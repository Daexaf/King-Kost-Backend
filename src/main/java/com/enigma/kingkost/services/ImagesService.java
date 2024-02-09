package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.entities.Customer;
import com.enigma.kingkost.entities.Images;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.stream.Stream;

public interface ImagesService {

    Images store(MultipartFile file) throws IOException;

    Images getImages(String id);

    Stream<Images> getAllImages();

    Customer updateProfileWithImage(String customerId, CustomerRequest updateRequest, MultipartFile profileImage) throws IOException;

}
