package com.enigma.kingkost.services.impl;
import com.enigma.kingkost.entities.MonthType;
import com.enigma.kingkost.repositories.MonthRepository;
import com.enigma.kingkost.services.MonthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MonthServiceImpl implements MonthService {

    private final MonthRepository monthRepository;

    @Override
    public MonthType getOrSave(MonthType month) {
        Optional<MonthType> optionalMonth = monthRepository.findById(month.getId());
        if (!optionalMonth.isEmpty()) {
            return optionalMonth.get();
        }
        return monthRepository.save(month);
    }

    @Override
    public MonthType getById(String id) {
        MonthType month = monthRepository.findById(id).orElse(null);
        assert month != null;
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
