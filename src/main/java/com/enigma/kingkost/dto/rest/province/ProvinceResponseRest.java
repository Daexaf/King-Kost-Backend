package com.enigma.kingkost.dto.rest.province;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ProvinceResponseRest {
    private String id;
    private String name;
}
