package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.rest.subdistrict.SubdistrictResponse;
import com.enigma.kingkost.entities.Subdistrict;

import java.util.List;

public interface SubdistrictService {
    void saveToDatabase();

    List<SubdistrictResponse> getByCityId(String id);

    List<SubdistrictResponse> getAll();
    Subdistrict getSubdistrictById(String id);
}
