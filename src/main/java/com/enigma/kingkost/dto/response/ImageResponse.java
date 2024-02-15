package com.enigma.kingkost.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ImageResponse {
    private String id;
    private String url;
    private String kost_id;
    private Boolean isActive;
}
