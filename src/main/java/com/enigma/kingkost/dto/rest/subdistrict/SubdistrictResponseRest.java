package com.enigma.kingkost.dto.rest.subdistrict;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SubdistrictResponseRest {
    private String id;
    private String id_kabupaten;
    private String name;
}
