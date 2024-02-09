package com.enigma.kingkost.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class KostRequest {
    private String id;
    private String name;
    private String description;
    private Integer availableRoom;
    private Boolean isWifi;
    private Boolean isAc;
    private Boolean isParking;
    private Double price;
    private String genderId;
    private String sellerId;
    private String provinceId;
    private String cityId;
    private String subdistrictId;
    private MultipartFile[] image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

