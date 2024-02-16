package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.entities.KostPrice;
import com.enigma.kingkost.repositories.KostPriceRepository;
import com.enigma.kingkost.services.KostPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class KostPriceServiceImpl implements KostPriceService {
    private final KostPriceRepository kostPriceRepository;

    @Override
    public KostPrice save(KostPrice kostPrice) {
        return kostPriceRepository.save(kostPrice);
    }

    @Override
    public KostPrice update(KostPrice kostPrice) {
        KostPrice prevKostPrice = getByKostId(kostPrice.getKost().getId());
        return kostPriceRepository.save(KostPrice.builder()
                .id(prevKostPrice.getId())
                .price(prevKostPrice.getPrice())
                .kost(prevKostPrice.getKost())
                .isActive(false)
                .createdAt(prevKostPrice.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public KostPrice getById(String id) {
        KostPrice kostPrice = kostPriceRepository.findById(id).orElse(null);
        if (kostPrice == null) {
            throw new NotFoundException("Kost price not found");
        }
        return kostPrice;
    }

    @Override
    public void deleteKostPrice(String id) {
        KostPrice kostPrice = getByKostId(id);
        update(kostPrice);
    }

    @Override
    public KostPrice getByKostId(String kostId) {
        KostPrice kostPrice = kostPriceRepository.findByKost_IdAndIsActiveTrue(kostId);
        if (kostPrice == null) {
            throw new NotFoundException("Kost price with kost id " + kostId + " not found");
        }
        return kostPrice;
    }
}
