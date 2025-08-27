package com.porkolab.chinesezodiac.controller;

import com.porkolab.chinesezodiac.entity.ZodiacPersonality;
import com.porkolab.chinesezodiac.service.PersonalityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/personalities")
public class PersonalityController {

    private final PersonalityService personalityService;

    public PersonalityController(PersonalityService personalityService) {
        this.personalityService = personalityService;
    }

    @GetMapping("/{zodiacName}")
    public List<ZodiacPersonality> getPersonalitiesByZodiacName(@PathVariable String zodiacName) {
        return personalityService.getPersonalitiesByZodiacName(zodiacName);
    }
}
