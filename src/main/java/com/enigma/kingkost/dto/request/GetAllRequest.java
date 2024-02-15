package com.enigma.kingkost.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class GetAllRequest {
    private String province_id;
    private String city_id;
    private String subdistrict_id;
    private String name;
    private String gender_type_id;
    private Integer page;
    private Integer size;
    private Double maxPrice;
    private String sellerId;
}
