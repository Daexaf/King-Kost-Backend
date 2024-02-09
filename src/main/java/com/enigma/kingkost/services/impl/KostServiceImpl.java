package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.KostRequest;
import com.enigma.kingkost.dto.request.UpdateKostRequest;
import com.enigma.kingkost.dto.response.KostResponse;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponse;
import com.enigma.kingkost.entities.*;
import com.enigma.kingkost.repositories.KostRepository;
import com.enigma.kingkost.services.*;
import com.enigma.kingkost.services.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KostServiceImpl implements KostService {
    private final KostRepository kostRepository;
    private final ImageService imageService;
    private final FileStorageService fileStorageService;
    private final KostPriceService kostPriceService;
    private final ProvinceService provinceService;
    private final CityService cityService;
    private final SubdistrictService subdistrictService;
    private final SellerService sellerService;
    private final GenderService genderService;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public KostResponse createKostAndKostprice(KostRequest kostRequest) {
        ProvinceResponse findProvince = provinceService.getProvinceById(kostRequest.getProvinceId());
        List<CityResponse> listFindCity = cityService.getByProvinceId(findProvince.getId());
        City findCity = null;
        for (CityResponse cityResponse : listFindCity) {
            if (cityResponse.getId().equals(kostRequest.getCityId())) {
                findCity = City.builder()
                        .id(cityResponse.getId())
                        .name(cityResponse.getName())
                        .province(cityResponse.getProvince())
                        .build();
            }
        }
        if (findCity == null) {
            throw new NotFoundException("City not found with id " + kostRequest.getCityId());
        }
        List<SubdistrictResponse> subdistrictResponseList = subdistrictService.getByCityId(findCity.getId());
        Subdistrict findSubdistrict = null;
        for (SubdistrictResponse subdistrictResponse : subdistrictResponseList) {
            if (subdistrictResponse.getId().equals(kostRequest.getSubdistrictId())) {
                findSubdistrict = Subdistrict.builder()
                        .id(subdistrictResponse.getId())
                        .name(subdistrictResponse.getName())
                        .city(subdistrictResponse.getCity())
                        .build();
            }
        }
        if (findSubdistrict == null) {
            throw new NotFoundException("Subdistrict not found with id " + kostRequest.getSubdistrictId());
        }

        SellerResponse findSeller = sellerService.getById(kostRequest.getSellerId());
        GenderType genderTypeSeller = genderService.getById(findSeller.getGenderTypeId());
        GenderType genderTypeKost = genderService.getById(kostRequest.getGenderId());

        Kost saveKost = kostRepository.save(Kost.builder()
                .name(kostRequest.getName())
                .description(kostRequest.getDescription())
                .seller(Seller.builder()
                        .id(findSeller.getId())
                        .fullName(findSeller.getFullName())
                        .email(findSeller.getEmail())
                        .address(findSeller.getAddress())
                        .phoneNumber(findSeller.getPhoneNumber())
                        .genderTypeId(genderTypeSeller)
                        .build())
                .genderType(genderTypeKost)
                .isAc(kostRequest.getIsAc())
                .isWifi(kostRequest.getIsWifi())
                .isParking(kostRequest.getIsParking())
                .availableRoom(kostRequest.getAvailableRoom())
                .createdAt(LocalDateTime.now())
                .province(Province.builder()
                        .id(findProvince.getId())
                        .name(findProvince.getName())
                        .createdAt(findProvince.getCreatedAt())
                        .build())
                .city(findCity)
                .subdistrict(findSubdistrict)
                .build());

        KostPrice kostPrice = kostPriceService.save(KostPrice.builder()
                .price(kostRequest.getPrice())
                .kost(saveKost)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .build());
        List<Image> listImageSave = new ArrayList<>();

        Arrays.stream(kostRequest.getImage()).forEach(image -> {
            String fileName = fileStorageService.storageFile(image);
            Image saveImage = Image.builder()
                    .fileName(fileName)
                    .isActive(true)
                    .kost(saveKost)
                    .createdAt(LocalDateTime.now())
                    .build();
            imageService.save(saveImage);
            listImageSave.add(saveImage);
        });
        if (listImageSave.isEmpty()) {
            throw new NullPointerException("List image null");
        }
        return KostResponse.builder()
                .id(saveKost.getId())
                .name(saveKost.getName())
                .description(saveKost.getDescription())
                .genderType(saveKost.getGenderType())
                .availableRoom(saveKost.getAvailableRoom())
                .isWifi(saveKost.getIsWifi())
                .isAc(saveKost.getIsAc())
                .isParking(saveKost.getIsParking())
                .seller(findSeller)
                .province(findProvince)
                .city(CityResponse.builder()
                        .id(findCity.getId())
                        .name(findCity.getName())
                        .province(findCity.getProvince())
                        .build())
                .subdistrict(SubdistrictResponse.builder()
                        .id(findSubdistrict.getId())
                        .name(findSubdistrict.getName())
                        .city(findSubdistrict.getCity())
                        .build())
                .seller(findSeller)
                .createdAt(saveKost.getCreatedAt())
                .images(listImageSave)
                .kostPrice(kostPrice)
                .build();
    }

    @Override
    public List<KostResponse> getAll() {
        return null;
    }

    @Override
    public Kost getById(String id) {
        Kost kost = kostRepository.findById(id).orElse(null);
        if (kost == null) {
            throw new NotFoundException("Kost with id " + id + " not found");
        }
        return kost;
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public KostResponse updateKost(UpdateKostRequest kostRequest) {
        SellerResponse sellerResponse = sellerService.getById(kostRequest.getSellerId());
        Kost findKost = getById(kostRequest.getId());
        KostPrice kostPrice = kostPriceService.getByKostId(findKost.getId());
        GenderType genderTypeSeller = genderService.getById(sellerResponse.getGenderTypeId());
        GenderType genderTypeKost = genderService.getById(kostRequest.getGenderId());
        ProvinceResponse provinceResponse = provinceService.getProvinceById(kostRequest.getId());
        CityResponse cityResponses = (CityResponse) cityService.getByProvinceId(provinceResponse.getId()).stream().filter(cityResponse -> cityResponse.getId().equals(kostRequest.getCityId()));
        if (cityResponses == null) {
            throw new NullPointerException("City response its null");
        }
        SubdistrictResponse subdistrictResponses = (SubdistrictResponse) subdistrictService.getByCityId(kostRequest.getCityId()).stream().filter(subdistrictResponse -> subdistrictResponse.getId().equals(kostRequest.getSubdistrictId()));
        if (subdistrictResponses == null) {
            throw new NullPointerException("Sub district with id " + kostRequest.getSubdistrictId() + " is null");
        }
        Kost saveKost = kostRepository.save(Kost.builder()
                .id(kostRequest.getId())
                .name(kostRequest.getName())
                .description(kostRequest.getDescription())
                .availableRoom(kostRequest.getAvailableRoom())
                .isWifi(kostRequest.getIsWifi())
                .isAc(kostRequest.getIsAc())
                .isParking(kostRequest.getIsParking())
                .genderType(genderTypeKost)
                .seller(Seller.builder()
                        .id(sellerResponse.getId())
                        .fullName(sellerResponse.getFullName())
                        .email(sellerResponse.getEmail())
                        .address(sellerResponse.getAddress())
                        .phoneNumber(sellerResponse.getPhoneNumber())
                        .genderTypeId(genderTypeSeller)
                        .build())
                .province(Province.builder()
                        .id(provinceResponse.getId())
                        .name(provinceResponse.getName())
                        .createdAt(provinceResponse.getCreatedAt())
                        .build())
                .city(City.builder()
                        .id(cityResponses.getId())
                        .name(cityResponses.getName())
                        .province(cityResponses.getProvince())
                        .build())
                .subdistrict(Subdistrict.builder()
                        .id(subdistrictResponses.getId())
                        .name(subdistrictResponses.getName())
                        .city(City.builder()
                                .id(subdistrictResponses.getId())
                                .province(Province.builder()
                                        .id(provinceResponse.getId())
                                        .name(provinceResponse.getName())
                                        .createdAt(provinceResponse.getCreatedAt())
                                        .build())
                                .build())
                        .build())
                .createdAt(kostRequest.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build());

        KostPrice saveKostPrice;
        if (kostRequest.getPrice().equals(kostPrice.getPrice())) {
            saveKostPrice = kostPriceService.save(kostPrice);
        } else {
            kostPriceService.update(kostPrice);
            saveKostPrice = kostPriceService.save(KostPrice.builder()
                    .price(kostRequest.getPrice())
                    .createdAt(LocalDateTime.now())
                    .isActive(true)
                    .kost(Kost.builder()
                            .id(findKost.getId())
                            .name(saveKost.getName())
                            .description(saveKost.getDescription())
                            .availableRoom(saveKost.getAvailableRoom())
                            .seller(Seller.builder()
                                    .id(sellerResponse.getId())
                                    .fullName(sellerResponse.getFullName())
                                    .email(sellerResponse.getEmail())
                                    .address(sellerResponse.getAddress())
                                    .phoneNumber(sellerResponse.getPhoneNumber())
                                    .genderTypeId(genderTypeSeller)
                                    .build())
                            .isParking(saveKost.getIsParking())
                            .isWifi(saveKost.getIsWifi())
                            .isAc(saveKost.getIsAc())
                            .genderType(genderTypeKost)
                            .province(Province.builder()
                                    .id(provinceResponse.getId())
                                    .name(provinceResponse.getName())
                                    .createdAt(provinceResponse.getCreatedAt())
                                    .build())
                            .city(City.builder()
                                    .id(cityResponses.getId())
                                    .name(cityResponses.getName())
                                    .province(cityResponses.getProvince())
                                    .build())
                            .subdistrict(Subdistrict.builder()
                                    .id(subdistrictResponses.getId())
                                    .name(subdistrictResponses.getName())
                                    .city(City.builder()
                                            .id(subdistrictResponses.getId())
                                            .province(Province.builder()
                                                    .id(provinceResponse.getId())
                                                    .name(provinceResponse.getName())
                                                    .createdAt(provinceResponse.getCreatedAt())
                                                    .build())
                                            .build())
                                    .build())
                            .build())
                    .createdAt(LocalDateTime.now())
                    .build());
        }

        for (Image prevImage : kostRequest.getListImage()) {
            if (prevImage.getIsActive().equals(false)) {
                imageService.deleteImage(prevImage);
            }
        }

        for (MultipartFile fileImage: kostRequest.getFileImages()) {
          String imageNameAfterStore = fileStorageService.storageFile(fileImage);
          imageService.save(Image.builder()
                          .fileName(imageNameAfterStore)
                          .isActive(true)
                          .kost(saveKost)
                          .createdAt(LocalDateTime.now())
                  .build());
        }

        List<Image> images = imageService.getImageByKostId(saveKost.getId());
        return KostResponse.builder()
                .id(saveKost.getId())
                .name(saveKost.getName())
                .description(saveKost.getDescription())
                .kostPrice(saveKostPrice)
                .availableRoom(saveKost.getAvailableRoom())
                .isParking(saveKost.getIsParking())
                .isWifi(saveKost.getIsWifi())
                .isAc(saveKost.getIsAc())
                .genderType(genderTypeKost)
                .seller(sellerResponse)
                .province(provinceResponse)
                .city(cityResponses)
                .subdistrict(subdistrictResponses)
                .images(images)
                .createdAt(saveKost.getCreatedAt())
                .updatedAt(saveKost.getUpdatedAt())
                .build();
    }

    @Override
    public void deleteKost(String id) {

    }
}
