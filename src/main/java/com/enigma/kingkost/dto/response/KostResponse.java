package com.enigma.kingkost.dto.response;

import com.enigma.kingkost.dto.rest.city.CityResponse;
import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponse;
import com.enigma.kingkost.entities.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class KostResponse {
    private String id;
    private String name;
    private String description;
    private KostPrice kostPrice;
    private Integer availableRoom;
    private Boolean isWifi;
    private Boolean isAc;
    private Boolean isParking;
    private GenderType genderType;
    private SellerResponse seller;
    private List<ImageResponse> images;
    private ProvinceResponse province;
    private CityResponse city;
    private Boolean isActive;
    private SubdistrictResponse subdistrict;
    private Integer currentBookingStatus;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
