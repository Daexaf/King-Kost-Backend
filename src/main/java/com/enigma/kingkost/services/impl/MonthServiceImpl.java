package com.enigma.kingkost.services.impl;

import com.enigma.kingkost.constant.EMonth;
import com.enigma.kingkost.entities.MonthType;
import com.enigma.kingkost.repositories.MonthRepository;
import com.enigma.kingkost.services.MonthService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MonthServiceImpl implements MonthService {

    private final MonthRepository monthRepository;

    @PostConstruct
    @Override
    public void autoCreate() {
        List<MonthType> monthTypes = getAll();
        if (monthTypes.isEmpty()) {
            Arrays.stream(EMonth.values()).forEach(eMonth -> monthRepository.save(MonthType.builder()
                    .name(eMonth)
                    .build()));
        }
    }

    @Override
    public MonthType getById(String id) {
        MonthType month = monthRepository.findById(id).orElse(null);
        if (month == null) {
            throw new NullPointerException("Mont not found");
        }
        return month;
    }

    @Override
    public List<MonthType> getAll() {
        List<MonthType> month = monthRepository.findAll();
        return month;
    }

    @Override
    public MonthType createMonth(MonthType month) {
        return monthRepository.save(month);
    }
}
