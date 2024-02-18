package com.enigma.kingkost.controllers;

import com.enigma.kingkost.constant.AppPath;
import com.enigma.kingkost.entities.GenderType;
import com.enigma.kingkost.services.GenderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = AppPath.URL_CROSS)
@RestController
@RequiredArgsConstructor
@RequestMapping(AppPath.GENDER)
public class GenderController {

    private final GenderService genderService;

    @GetMapping(path = "/v1")
    List<GenderType> getGender() {
        return genderService.getAll();
    }

    @GetMapping(path = "/v1/{id}")
    GenderType getGenderById(@PathVariable String id) {
        return genderService.getById(id);
    }

    @PostMapping(path = "/v1")
    GenderType addGender(@RequestBody GenderType gender) {
        return genderService.createGender(gender);
    }
}
