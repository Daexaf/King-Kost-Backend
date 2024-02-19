package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.TransactionKost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionKostRepository extends JpaRepository<TransactionKost, String>, JpaSpecificationExecutor<TransactionKost> {
    TransactionKost getByKostIdAndCustomerIdAndAprStatusEquals(String kostId, String customerId, Integer status);
}
