package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.Kost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KostRepository extends JpaRepository<Kost, String> {
}
