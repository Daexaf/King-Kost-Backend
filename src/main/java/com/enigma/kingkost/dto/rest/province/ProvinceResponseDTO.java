package com.enigma.kingkost.dto.rest.province;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProvinceResponseDTO {
    private String code;
    private String messages;
    private ProvinceResponseRest[] value;
}
