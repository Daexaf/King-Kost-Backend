package com.enigma.kingkost.dto.request;

import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "description is required")
    private String description;
    @NotBlank(message = "availableRoom is required")
    private Integer availableRoom;
    @NotBlank(message = "isWifi is required")
    private Boolean isWifi;
    @NotBlank(message = "isAc is required")
    private Boolean isAc;
    @NotBlank(message = "v is required")
    private Boolean isParking;
    @NotBlank(message = "price is required")
    private Double price;
    @NotBlank(message = "genderId is required")
    private String genderId;
    @NotBlank(message = "sellerId is required")
    private String sellerId;
    @NotBlank(message = "provinceId is required")
    private String provinceId;
    @NotBlank(message = "cityId is required")
    private String cityId;
    @NotBlank(message = "subdistrictId is required")
    private String subdistrictId;
    @NotBlank(message = "image is required")
    private MultipartFile[] image;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

