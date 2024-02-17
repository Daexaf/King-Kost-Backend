package com.enigma.kingkost.services;

import com.enigma.kingkost.entities.MonthType;

import java.util.List;

public interface MonthService {

    MonthType getOrSave(MonthType month);

    MonthType getById(String id);

    List<MonthType> getAll();

    MonthType createMonth(MonthType month);
}
