package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.CustomerRequest;
import com.enigma.kingkost.dto.response.CustomerResponse;
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
    private final Path fileStore = Paths.get("/home/user/Batch14/Final/src/main/resources/static");
//    private final ImagesService imagesService;
//    private final CustomerRepository customerRepository;
//    private final SellerRepository sellerRepository;

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

//    @Override
//    public CustomerResponse addOrUpdateProfileImageForCustomer(String customerId, MultipartFile profileImage) throws IOException {
////        Images image = imagesService.store(profileImage);
////        Customer customer = customerRepository.findById(customerId).orElse(null);
////
////        if (customer != null) {
////            // Update informasi gambar profil pada entitas pelanggan
////            customer.setProfileImageName(image.getName());
////            customer.setProfileImageType(image.getType());
////            customer.setProfileImageData(image.getData());
////
////            // Lakukan operasi update pada repositori pelanggan
////            Customer updatedCustomer = customerRepository.save(customer);
////
////            // Konversi entitas pelanggan yang telah diperbarui ke DTO (CustomerResponse)
////            return convertToResponse(updatedCustomer);
////        }
////
//        return null;
//    }
//
//    @Override
//    public SellerResponse addOrUpdateProfileImageForSeller(String sellerId, MultipartFile profileImage) throws IOException {
////        // Menggunakan ImagesService untuk menyimpan gambar profil
////        Images image = imagesService.store(profileImage);
////
////        // Mengambil entitas penjual dari repositori
////        Seller seller = sellerRepository.findById(sellerId).orElse(null);
////
////        if (seller != null) {
////            // Update informasi gambar profil pada entitas penjual
////            seller.setProfileImageName(image.getName());
////            seller.setProfileImageType(image.getType());
////            seller.setProfileImageData(image.getData());
////
////            // Lakukan operasi update pada repositori penjual
////            Seller updatedSeller = sellerRepository.save(seller);
////
////            // Konversi entitas penjual yang telah diperbarui ke DTO (SellerResponse)
////            return convertToResponse(updatedSeller);
////        }
//        return null;
//    }

//    private CustomerResponse convertToResponse(Customer customer) {
//        return CustomerResponse.builder()
//                .id(customer.getId())
//                .fullName(customer.getFullName())
//                .email(customer.getEmail())
//                .genderTypeId(customer.getGenderTypeId())
//                .phoneNumber(customer.getPhoneNumber())
//                .address(customer.getAddress())
//                .profileImageName(customer.getProfileImageName())
//                .profileImageType(customer.getProfileImageType())
//                .profileImageData(customer.getProfileImageData())
//                .build();
//    }
//
//    private SellerResponse convertToResponse(Seller seller) {
//        return SellerResponse.builder()
//                .id(seller.getId())
//                .fullName(seller.getFullName())
//                .email(seller.getEmail())
//                .genderTypeId(seller.getGenderTypeId().getId())
//                .phoneNumber(seller.getPhoneNumber())
//                .address(seller.getAddress())
//                .profileImageName(seller.getProfileImageName())
//                .profileImageType(seller.getProfileImageType())
//                .profileImageData(seller.getProfileImageData())
//                .build();
//    }
}
