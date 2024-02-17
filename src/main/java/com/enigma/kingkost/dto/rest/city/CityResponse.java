package com.enigma.kingkost.dto.rest.city;

import com.enigma.kingkost.entities.Province;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class CityResponse {
    private String id;
    private String name;
    private Province province;
}
