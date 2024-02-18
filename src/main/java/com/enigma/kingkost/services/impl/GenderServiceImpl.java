package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.constant.EGender;
import com.enigma.kingkost.entities.GenderType;
import com.enigma.kingkost.repositories.GenderRepository;
import com.enigma.kingkost.services.GenderService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenderServiceImpl implements GenderService {

    private final GenderRepository genderRepository;

    @PostConstruct
    @Override
    public void autoCreate() {
        List<GenderType> checkGender = getAll();
        if (checkGender.isEmpty()) {
            Arrays.stream(EGender.values()).forEach(eGender -> genderRepository.save(GenderType.builder()
                    .name(eGender)
                    .build()));
        }
    }

    @Override
    public GenderType getById(String id) {
        GenderType gender = genderRepository.findById(id).orElse(null);
        if (gender == null) {
            throw new NullPointerException("Gender not found");
        }
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
