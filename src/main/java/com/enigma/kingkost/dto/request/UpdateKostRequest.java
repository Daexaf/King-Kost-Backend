package com.enigma.kingkost.dto.request;

import com.enigma.kingkost.dto.response.ImageResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class UpdateKostRequest {
    @NotBlank(message = "provinceId is required")
    private String id;
    @NotBlank(message = "name is required")
    private String name;
    @NotBlank(message = "description is required")
    private String description;
    @NotNull(message = "availableRoom is required")
    private Integer availableRoom;
    @NotNull(message = "isWifi is required")
    private Boolean isWifi;
    @NotNull(message = "isAc is required")
    private Boolean isAc;
    @NotNull(message = "isParking is required")
    private Boolean isParking;
    @NotNull(message = "price is required")
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
    @NotNull(message = "listImage is required")
    private List<ImageResponse> listImage;
    @NotNull(message = "createdAt is required")
    private LocalDateTime createdAt;
}
