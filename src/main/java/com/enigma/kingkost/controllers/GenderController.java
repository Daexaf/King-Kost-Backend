package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.entities.GenderType;
import com.enigma.kingkost.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.GENDER)
public class GenderController {

    private final GenderService genderService;

    @GetMapping(path = "/gender")
    List<GenderType> getGender() {
        return genderService.getAll();
    }

    @GetMapping(path = "/gender/{id}")
    GenderType getGenderById(@PathVariable String id) {
        return genderService.getById(id);
    }

    @PostMapping(path = "/create")
    GenderType addGender(@RequestBody GenderType gender) {
        return genderService.createGender(gender);
    }
}
