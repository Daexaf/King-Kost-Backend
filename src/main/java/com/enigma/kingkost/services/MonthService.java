package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.MonthType;

import java.util.List;

public interface MonthService {

    void autoCreate();

    MonthType getById(String id);

    List<MonthType> getAll();

    MonthType createMonth(MonthType month);
}
