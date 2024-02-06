package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.entities.GenderType;
import com.enigma.kingkost.repositories.GenderRepository;
import com.enigma.kingkost.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;

    @Override
    public GenderType getOrSave(GenderType gender) {
        Optional<GenderType> optionalGender = genderRepository.findById(gender.getId());
        if (!optionalGender.isEmpty()) {
            return optionalGender.get();
        }
        return genderRepository.save(gender);
    }

    @Override
    public GenderType getById(String id) {
        GenderType gender = genderRepository.findById(id).orElse(null);
        assert gender != null;
        return gender;
    }

    @Override
    public List<GenderType> getAll() {
        List<GenderType> genders = genderRepository.findAll();
        return genders;
    }

    @Override
    public GenderType createGender(GenderType gender) {
        return genderRepository.save(gender);
    }
}
