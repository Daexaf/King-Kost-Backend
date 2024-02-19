package com.enigma.kingkost.services;

import com.enigma.kingkost.dto.request.GetAllRequest;
import com.enigma.kingkost.dto.request.KostRequest;
import com.enigma.kingkost.dto.request.UpdateImageKostRequest;
import com.enigma.kingkost.dto.request.UpdateKostRequest;
import com.enigma.kingkost.dto.response.KostResponse;
import com.enigma.kingkost.entities.Kost;
import org.springframework.data.domain.Page;

public interface KostService {
    KostResponse createKostAndKostprice(KostRequest kostRequest);
    Page<KostResponse> getAll(GetAllRequest getAllRequest);
    Kost getById(String id);
    KostResponse updateKost(UpdateKostRequest kostRequest);
    void deleteKost(String id);
    KostResponse getByIdKost(String id, String customerId);
    void updateImageKost(UpdateImageKostRequest updateImageKostRequest);
    void ReduceItAvailableRoom(Kost kost);
    void addAvailableRoom(Kost kost);
}
