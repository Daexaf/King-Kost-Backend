package com.enigma.kingkost.dto.rest.city;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CityResponseDTO {
    private String code;
    private String messages;
    private CityResponseRest[] value;
}

