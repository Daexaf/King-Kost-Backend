package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.TransactionKost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionKostRepository extends JpaRepository<TransactionKost, String> {
    TransactionKost findByKostSellerIdAndAprStatusLessThan(String sellerId, Integer approv);
    List<TransactionKost> findByCustomerId(String customerId);
    List<TransactionKost> findByKostSellerId(String sellerId);
}
