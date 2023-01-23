package com.porkolab.chinesezodiac.controller;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.porkolab.chinesezodiac.entity.Zodiac;
import com.porkolab.chinesezodiac.service.ZodiacService;

@RestController
@RequestMapping("/zodiacs")
public class ZodiacController {

    private final ZodiacService zodiacService;

    public ZodiacController(ZodiacService zodiacService) {
        this.zodiacService = zodiacService;
    }

    @GetMapping
    public List<Zodiac> findAll() {
        return zodiacService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Zodiac> findById(@PathVariable Long id) {
        return zodiacService.findById(id);
    }

    @PostMapping
    public Zodiac save(@RequestBody Zodiac zodiac) {
        return zodiacService.save(zodiac);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        zodiacService.deleteById(id);
    }

    @GetMapping("/findByName")
    public Zodiac findByName(@RequestParam String name) {
        return zodiacService.findByName(name);
    }

    @GetMapping("/findByDate")
    public List<Zodiac> findByDate(@RequestParam("date") @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        return zodiacService.findByDate(date);
    }

    @PutMapping("/{id}")
    public Zodiac update(@PathVariable Long id, @RequestBody Zodiac zodiac) {
        return zodiacService.update(id, zodiac);
    }
}
