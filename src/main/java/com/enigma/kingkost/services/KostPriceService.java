package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.KostPrice;

public interface KostPriceService {
    KostPrice save(KostPrice kostPrice);
    KostPrice update(KostPrice kostPrice);
    KostPrice getById(String id);
    void deleteKostPrice(String id);
    KostPrice getByKostId(String kostId);
}
