package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.KostRequest;
import com.enigma.kingkost.dto.request.UpdateKostRequest;
import com.enigma.kingkost.dto.response.KostResponse;
import com.enigma.kingkost.entities.Kost;

import java.util.List;

public interface KostService {
    KostResponse createKostAndKostprice(KostRequest kostRequest);
    List<KostResponse> getAll();
    Kost getById(String id);
    KostResponse updateKost(UpdateKostRequest kostRequest);
    void deleteKost(String id);
}
