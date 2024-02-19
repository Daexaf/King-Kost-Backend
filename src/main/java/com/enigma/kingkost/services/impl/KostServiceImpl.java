package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.dto.request.GetAllRequest;
import com.enigma.kingkost.dto.request.KostRequest;
import com.enigma.kingkost.dto.request.UpdateImageKostRequest;
import com.enigma.kingkost.dto.request.UpdateKostRequest;
import com.enigma.kingkost.dto.response.ImageResponse;
import com.enigma.kingkost.dto.response.KostResponse;
import com.enigma.kingkost.dto.response.SellerResponse;
import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponse;
import com.enigma.kingkost.entities.*;
import com.enigma.kingkost.mapper.*;
import com.enigma.kingkost.repositories.KostRepository;
import com.enigma.kingkost.repositories.TransactionKostRepository;
import com.enigma.kingkost.repositories.UserCredentialRepository;
import com.enigma.kingkost.services.*;
import com.enigma.kingkost.services.FileStorageService;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KostServiceImpl implements KostService {
    private final KostRepository kostRepository;
    private final ImageKostService imageKostService;
    private final FileStorageService fileStorageService;
    private final KostPriceService kostPriceService;
    private final ProvinceService provinceService;
    private final CityService cityService;
    private final SubdistrictService subdistrictService;
    private final SellerService sellerService;
    private final GenderService genderService;
    private final TransactionKostRepository transactionKostRepository;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public KostResponse createKostAndKostprice(KostRequest kostRequest) {
        List<Kost> checkExistingKost = kostRepository.findKostBySellerId(kostRequest.getSellerId());
        for (Kost kost : checkExistingKost) {
            if (kost.getName().equals(kostRequest.getName()) && kost.getSubdistrict().getId().equals(kostRequest.getSubdistrictId())) {
                throw new NullPointerException("Cannot same post kost");
            }
        }
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
                        .genderTypeId(findSeller.getGenderTypeId())
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
                        .build())
                .city(findCity)
                .subdistrict(findSubdistrict)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());

        KostPrice kostPrice = kostPriceService.save(KostPrice.builder()
                .price(kostRequest.getPrice())
                .kost(saveKost)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
        List<Image> listImageSave = new ArrayList<>();

        Arrays.stream(kostRequest.getImage()).forEach(image -> {
            try {
                String urlImage = fileStorageService.uploadFile(image);
                Image saveImage = imageKostService.save(Image.builder()
                        .url(urlImage)
                        .isActive(true)
                        .kost(saveKost)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
                listImageSave.add(saveImage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
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
                .images(ImageMapper.listImageToListImageResponse(listImageSave))
                .kostPrice(kostPrice)
                .currentBookingStatus(4)
                .build();
    }

    @Override
    public Page<KostResponse> getAll(GetAllRequest getAllRequest) {
        Specification<Kost> specification = ((root, query, criteriaBuilder) -> {
            Join<Kost, KostPrice> kostPriceJoin = root.join("kostPrices");
            Join<Kost, Province> kostProvinceJoin = root.join("province");
            Join<Kost, City> kostCityJoin = root.join("city");
            Join<Kost, Subdistrict> kostSubdistrictJoin = root.join("subdistrict");
            Join<Kost, GenderType> kostGenderTypeJoin = root.join("genderType");
            Join<Kost, Seller> kostSellerJoin = root.join("seller");

            List<Predicate> predicates = new ArrayList<>();
            if (getAllRequest.getSellerId() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(kostSellerJoin.get("id")), '%' + getAllRequest.getSellerId().toLowerCase() + '%'));
            }
            if (getAllRequest.getName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), '%' + getAllRequest.getName().toLowerCase() + '%'));
            }
            if (getAllRequest.getMaxPrice() != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(kostPriceJoin.get("price"), getAllRequest.getMaxPrice()));
            }
            if (getAllRequest.getProvince_id() != null) {
                predicates.add(criteriaBuilder.equal(kostProvinceJoin.get("id"), getAllRequest.getProvince_id()));
            }
            if (getAllRequest.getCity_id() != null) {
                predicates.add(criteriaBuilder.equal(kostCityJoin.get("id"), getAllRequest.getCity_id()));
            }
            if (getAllRequest.getSubdistrict_id() != null) {
                predicates.add(criteriaBuilder.equal(kostSubdistrictJoin.get("id"), getAllRequest.getSubdistrict_id()));
            }
            if (getAllRequest.getGender_type_id() != null) {
                predicates.add(criteriaBuilder.equal(kostGenderTypeJoin.get("id"), getAllRequest.getGender_type_id()));
            }
            return query.where(predicates.toArray(new Predicate[]{})).getRestriction();
        });

        Pageable pageable = PageRequest.of(getAllRequest.getPage(), getAllRequest.getSize());
        Page<Kost> kosts = kostRepository.findAll(specification, pageable);
        List<KostResponse> kostResponses = new ArrayList<>();

        for (Kost kost : kosts.getContent()) {
            if (kost.getIsActive().equals(true)) {
                Optional<KostPrice> kostPrice = kost.getKostPrices().stream().filter(KostPrice::getIsActive).findFirst();
                if (kostPrice.isEmpty()) {
                    continue;
                }
                List<ImageResponse> imageList = imageKostService.getImageByKostId(kost.getId());
                kostResponses.add(KostResponse.builder()
                        .id(kost.getId())
                        .name(kost.getName())
                        .description(kost.getDescription())
                        .kostPrice(kostPrice.get())
                        .availableRoom(kost.getAvailableRoom())
                        .isWifi(kost.getIsWifi())
                        .isAc(kost.getIsAc())
                        .isParking(kost.getIsParking())
                        .genderType(kost.getGenderType())
                        .seller(SellerResponse.builder()
                                .id(kost.getSeller().getId())
                                .fullName(kost.getSeller().getFullName())
                                .username(kost.getSeller().getUserCredential().getUsername())
                                .address(kost.getSeller().getAddress())
                                .email(kost.getSeller().getEmail())
                                .phoneNumber(kost.getSeller().getPhoneNumber())
                                .genderTypeId(kost.getSeller().getGenderTypeId())
                                .build())
                        .images(imageList)
                        .province(ProvinceMapper.provinceToProvinceResponse(kost.getProvince()))
                        .city(CityMapper.cityToCityResponse(kost.getCity()))
                        .currentBookingStatus(4)
                        .subdistrict(SubdistrictMapper.subdistrictToSubdistrictResponse(kost.getSubdistrict()))
                        .build());
            }

        }
        return new PageImpl<>(kostResponses, pageable, kosts.getTotalElements());
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
        GenderType genderTypeSeller = genderService.getById(sellerResponse.getGenderTypeId().getId());
        GenderType genderTypeKost = genderService.getById(kostRequest.getGenderId());
        ProvinceResponse provinceResponse = provinceService.getProvinceById(kostRequest.getProvinceId());
        City city = cityService.getCityById(kostRequest.getCityId());
        Subdistrict subdistrict = subdistrictService.getSubdistrictById(kostRequest.getSubdistrictId());
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
                        .build())
                .city(City.builder()
                        .id(city.getId())
                        .name(city.getName())
                        .province(city.getProvince())
                        .build())
                .subdistrict(Subdistrict.builder()
                        .id(subdistrict.getId())
                        .name(subdistrict.getName())
                        .city(City.builder()
                                .id(city.getId())
                                .name(city.getName())
                                .province(Province.builder()
                                        .id(provinceResponse.getId())
                                        .name(provinceResponse.getName())
                                        .build())
                                .build())
                        .build())
                .isActive(findKost.getIsActive())
                .createdAt(findKost.getCreatedAt())
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
                                    .build())
                            .city(City.builder()
                                    .id(city.getId())
                                    .name(city.getName())
                                    .province(city.getProvince())
                                    .build())
                            .subdistrict(Subdistrict.builder()
                                    .id(subdistrict.getId())
                                    .name(subdistrict.getName())
                                    .city(City.builder()
                                            .id(city.getId())
                                            .name(city.getName())
                                            .province(Province.builder()
                                                    .id(provinceResponse.getId())
                                                    .name(provinceResponse.getName())
                                                    .build())
                                            .build())
                                    .build())
                            .build())
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());
        }

        for (ImageResponse prevImage : kostRequest.getListImage()) {
            if (prevImage.getIsActive().equals(false)) {
                imageKostService.deleteImage(prevImage, findKost);
            }
        }

        List<ImageResponse> images = imageKostService.getImageByKostId(saveKost.getId());
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
                .city(CityMapper.cityToCityResponse(city))
                .subdistrict(SubdistrictMapper.subdistrictToSubdistrictResponse(subdistrict))
                .images(images)
                .currentBookingStatus(4)
                .createdAt(saveKost.getCreatedAt())
                .updatedAt(saveKost.getUpdatedAt())
                .build();
    }

    @Override
    public void deleteKost(String id) {
        Kost kost = getById(id);
        kostRepository.save(Kost.builder()
                .id(kost.getId())
                .name(kost.getName())
                .description(kost.getDescription())
                .availableRoom(kost.getAvailableRoom())
                .seller(Seller.builder()
                        .id(kost.getSeller().getId())
                        .fullName(kost.getSeller().getFullName())
                        .email(kost.getSeller().getEmail())
                        .address(kost.getSeller().getAddress())
                        .phoneNumber(kost.getSeller().getPhoneNumber())
                        .genderTypeId(kost.getSeller().getGenderTypeId())
                        .build())
                .isParking(kost.getIsParking())
                .isWifi(kost.getIsWifi())
                .isAc(kost.getIsAc())
                .genderType(kost.getGenderType())
                .province(Province.builder()
                        .id(kost.getProvince().getId())
                        .name(kost.getProvince().getName())
                        .build())
                .city(City.builder()
                        .id(kost.getCity().getId())
                        .name(kost.getCity().getName())
                        .province(kost.getCity().getProvince())
                        .build())
                .subdistrict(Subdistrict.builder()
                        .id(kost.getSubdistrict().getId())
                        .name(kost.getSubdistrict().getName())
                        .city(City.builder()
                                .id(kost.getSubdistrict().getId())
                                .province(Province.builder()
                                        .id(kost.getProvince().getId())
                                        .name(kost.getProvince().getName())
                                        .build())
                                .build())
                        .build())
                .isActive(false)
                .createdAt(kost.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public KostResponse getByIdKost(String id, String customerId) {
        Kost kost = getById(id);
        KostPrice kostPrice = kostPriceService.getByKostId(kost.getId());
        List<Image> imageList = imageKostService.getByKostId(kost.getId());
        TransactionKost transactionKost = transactionKostRepository.getByKostIdAndCustomerIdAndAprStatusEquals(kost.getId(), customerId, 0);
        if (transactionKost != null) {
            return KostMapper.kostToKostResponse(kost, kostPrice, imageList, transactionKost.getAprStatus());
        }
        return KostMapper.kostToKostResponse(kost, kostPrice, imageList, 4);
    }

    @Override
    public void updateImageKost(UpdateImageKostRequest updateImageKostRequest) {
        Kost kost = getById(updateImageKostRequest.getKost_id());
        for (MultipartFile fileImage : updateImageKostRequest.getFileImages()) {
            try {
                String urlImage = fileStorageService.uploadFile(fileImage);
                if (urlImage == null) {
                    throw new NullPointerException("File image null");
                }
                imageKostService.save(Image.builder()
                        .url(urlImage)
                        .kost(kost)
                        .isActive(true)
                        .createdAt(LocalDateTime.now())
                        .build());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void ReduceItAvailableRoom(Kost kost) {
        kostRepository.save(Kost.builder()
                .id(kost.getId())
                .name(kost.getName())
                .description(kost.getDescription())
                .availableRoom(kost.getAvailableRoom() - 1)
                .seller(kost.getSeller())
                .isWifi(kost.getIsWifi())
                .isAc(kost.getIsAc())
                .isParking(kost.getIsParking())
                .genderType(kost.getGenderType())
                .province(kost.getProvince())
                .city(kost.getCity())
                .subdistrict(kost.getSubdistrict())
                .kostPrices(kost.getKostPrices())
                .isActive(kost.getIsActive())
                .createdAt(kost.getCreatedAt())
                .updatedAt(kost.getUpdatedAt())
                .build());
    }

    @Override
    public void addAvailableRoom(Kost kost) {
        kostRepository.save(Kost.builder()
                .id(kost.getId())
                .name(kost.getName())
                .description(kost.getDescription())
                .availableRoom(kost.getAvailableRoom() + 1)
                .seller(kost.getSeller())
                .isWifi(kost.getIsWifi())
                .isAc(kost.getIsAc())
                .isParking(kost.getIsParking())
                .genderType(kost.getGenderType())
                .province(kost.getProvince())
                .city(kost.getCity())
                .subdistrict(kost.getSubdistrict())
                .kostPrices(kost.getKostPrices())
                .isActive(kost.getIsActive())
                .createdAt(kost.getCreatedAt())
                .updatedAt(kost.getUpdatedAt())
                .build());
    }

}
