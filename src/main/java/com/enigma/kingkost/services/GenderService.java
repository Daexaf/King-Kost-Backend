package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.GenderType;

import java.util.List;

public interface GenderService {
    GenderType getOrSave(GenderType gender);

    GenderType getById(String id);

    List<GenderType> getAll();

    GenderType createGender(GenderType gender);
}
