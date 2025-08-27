package com.porkolab.chinesezodiac.controller;

import com.porkolab.chinesezodiac.entity.LifeStage;
import com.porkolab.chinesezodiac.service.LifeStageService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;


import java.util.List;

@RestController
@RequestMapping("/api/lifestages")
@CrossOrigin(origins = "http://localhost:4200")
public class LifeStageController {

    private final LifeStageService lifeStageService;

    public LifeStageController(LifeStageService lifeStageService) {
        this.lifeStageService = lifeStageService;
    }

    @GetMapping("/{zodiacName}")
    public List<LifeStage> getLifeStagesByZodiacName(@PathVariable String zodiacName) {
        return lifeStageService.getLifeStagesByZodiacName(zodiacName);
    }
}
