package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.KostPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KostPriceRepository extends JpaRepository<KostPrice, String> {
    KostPrice findByKost_IdAndIsActiveTrue(String kostId);
}
