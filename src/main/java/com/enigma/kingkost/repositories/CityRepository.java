package com.enigma.kingkost.repositories;

import com.enigma.kingkost.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, String> {
    List<City> findByProvinceId(String id);
}
