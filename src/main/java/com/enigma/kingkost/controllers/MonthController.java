package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.entities.MonthType;
import com.enigma.kingkost.services.MonthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.MONTH)
public class MonthController {

    private final MonthService monthService;

    @GetMapping(path = "/v1")
    List<MonthType> getMonth(){
        return monthService.getAll();
    }

    @GetMapping(path = "/v1/{id}")
    MonthType getMonth(@PathVariable String id) {
        return monthService.getById(id);
    }

    @PostMapping(path = "/v1")
    MonthType addMonth(@RequestBody MonthType month) {
        return monthService.createMonth(month);
    }
}
