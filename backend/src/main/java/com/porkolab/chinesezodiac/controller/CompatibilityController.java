package com.porkolab.chinesezodiac.controller;

import com.porkolab.chinesezodiac.entity.Compatibility;
import com.porkolab.chinesezodiac.service.CompatibilityService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compatibilities")
public class CompatibilityController {

    private final CompatibilityService compatibilityService;

    public CompatibilityController(CompatibilityService compatibilityService) {
        this.compatibilityService = compatibilityService;
    }

    @GetMapping("/{zodiac1Name}/{zodiac2Name}")
    public List<Compatibility> getCompatibility(@PathVariable String zodiac1Name, @PathVariable String zodiac2Name) {
        return compatibilityService.getCompatibility(zodiac1Name, zodiac2Name);
    }
}
