package com.enigma.kingkost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ImagesResponse {
    private Long id;
    private String name;
    private String type;
    private byte[] data;
}