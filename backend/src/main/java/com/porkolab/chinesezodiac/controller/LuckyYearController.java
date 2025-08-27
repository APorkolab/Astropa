package com.porkolab.chinesezodiac.controller;

import com.porkolab.chinesezodiac.entity.LuckyYear;
import com.porkolab.chinesezodiac.service.LuckyYearService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lucky-years")
public class LuckyYearController {

    private final LuckyYearService luckyYearService;

    public LuckyYearController(LuckyYearService luckyYearService) {
        this.luckyYearService = luckyYearService;
    }

    @GetMapping("/{zodiacName}/{year}")
    public List<LuckyYear> getLuckyYears(@PathVariable String zodiacName, @PathVariable int year) {
        return luckyYearService.getLuckyYears(zodiacName, year);
    }
}
