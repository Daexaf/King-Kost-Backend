package com.enigma.kingkost.dto.request;

import com.enigma.kingkost.entities.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class UpdateKostRequest {
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
    private MultipartFile[] fileImages;
    private List<Image> listImage;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
