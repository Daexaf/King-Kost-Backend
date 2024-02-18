package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.Seller;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SellerRepository extends JpaRepository<Seller, String> {
    Optional<Seller> findByUserCredentialId(String userCredentialId);

    Optional<Seller> findByEmail(String email);
}
