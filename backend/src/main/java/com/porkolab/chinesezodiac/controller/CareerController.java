package com.porkolab.chinesezodiac.controller;

import com.porkolab.chinesezodiac.entity.ZodiacCareer;
import com.porkolab.chinesezodiac.service.CareerService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/careers")
public class CareerController {

    private final CareerService careerService;

    public CareerController(CareerService careerService) {
        this.careerService = careerService;
    }

    @GetMapping("/{zodiacName}")
    public List<ZodiacCareer> getCareersByZodiacName(@PathVariable String zodiacName) {
        return careerService.getCareersByZodiacName(zodiacName);
    }
}
