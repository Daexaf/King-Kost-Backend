package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.Subdistrict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface SubdistrictRepository extends JpaRepository<Subdistrict, String> {
    List<Subdistrict> findByCity_Id(String id);
}
