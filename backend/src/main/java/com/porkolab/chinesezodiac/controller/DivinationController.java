package com.porkolab.chinesezodiac.controller;

import com.porkolab.chinesezodiac.entity.Divination;
import com.porkolab.chinesezodiac.service.DivinationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/divinations")
@CrossOrigin(origins = "http://localhost:4200")
public class DivinationController {

    private final DivinationService divinationService;

    public DivinationController(DivinationService divinationService) {
        this.divinationService = divinationService;
    }

    @GetMapping("/{zodiacName}")
    public List<Divination> getDivinationsByZodiacName(@PathVariable String zodiacName) {
        return divinationService.getDivinationsByZodiacName(zodiacName);
    }
}
