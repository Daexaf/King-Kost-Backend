package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.Village;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VillageRepository extends JpaRepository<Village, String> {
}
