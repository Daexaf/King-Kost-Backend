package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Optional<Customer> findByUserCredentialId(String userCredentialId);

    Optional<Customer> findByEmail(String email);
}
