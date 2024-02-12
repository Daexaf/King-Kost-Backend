package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
import com.enigma.kingkost.dto.response.ImagesResponse;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.entities.Customer;
import com.enigma.kingkost.entities.Images;
import com.enigma.kingkost.entities.Seller;
import com.enigma.kingkost.repositories.CustomerRepository;
import com.enigma.kingkost.repositories.ImagesRepository;
import com.enigma.kingkost.repositories.SellerRepository;
import com.enigma.kingkost.services.CustomerService;
import com.enigma.kingkost.services.ImagesService;
import com.enigma.kingkost.services.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ImagesServiceImpl implements ImagesService {

    private final ImagesRepository imagesRepository;

    @Override
    public Images store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Images image = new Images();
        image.setName(fileName);
        image.setType(file.getContentType());
        image.setData(file.getBytes());
        return imagesRepository.save(image);
    }

    @Override
    public Images getImages(String id) {
        return imagesRepository.findById(id).orElse(null);
    }

    @Override
    public Stream<Images> getAllImages() {
        return imagesRepository.findAll().stream();
    }

}
