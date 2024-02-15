package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.rest.province.ProvinceResponse;
import java.util.List;

public interface ProvinceService {
    void saveProvinceToDatabase();
    List<ProvinceResponse> getAllProvince();
    ProvinceResponse getProvinceById(String id);
}
