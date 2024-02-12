package com.enigma.kingkost.mapper;

import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponse;
import com.enigma.kingkost.entities.Subdistrict;

import java.util.ArrayList;
import java.util.List;

public class SubdistrictMapper {
    public static List<SubdistrictResponse> subdistrictListToSubdistrictResponse(List<Subdistrict> subdistrictList) {
        List<SubdistrictResponse> subdistrictResponseList = new ArrayList<>();
        subdistrictList.forEach((subdistrict -> {
            subdistrictResponseList.add(SubdistrictResponse.builder()
                            .id(subdistrict.getId())
                            .name(subdistrict.getName())
                            .city(subdistrict.getCity())
                    .build());
        }));
        return subdistrictResponseList;
    }
    public static SubdistrictResponse subdistrictToSubdistrictResponse(Subdistrict subdistrict) {
        return SubdistrictResponse.builder()
                .id(subdistrict.getId())
                .name(subdistrict.getName())
                .city(subdistrict.getCity())
                .build();
    }
}
