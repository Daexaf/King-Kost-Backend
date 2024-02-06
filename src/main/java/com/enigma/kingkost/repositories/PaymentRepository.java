package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<PaymentType, String> {
}
