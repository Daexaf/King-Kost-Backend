package com.enigma.kingkost.dto.rest.subdistrict;

import com.enigma.kingkost.entities.City;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class SubdistrictResponse {
    private String id;
    private String name;
    private City city;
}
