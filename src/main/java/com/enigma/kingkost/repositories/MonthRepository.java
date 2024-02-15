package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.MonthType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthRepository extends JpaRepository<MonthType, String> {
}
