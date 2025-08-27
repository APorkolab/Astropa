package com.porkolab.chinesezodiac.service;

import com.porkolab.chinesezodiac.entity.LuckyYear;
import com.porkolab.chinesezodiac.repository.LuckyYearRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LuckyYearService {

    private final LuckyYearRepository luckyYearRepository;

    public LuckyYearService(LuckyYearRepository luckyYearRepository) {
        this.luckyYearRepository = luckyYearRepository;
    }

    public List<LuckyYear> getLuckyYears(String zodiacName, int year) {
        return luckyYearRepository.findByZodiacNameAndYear(zodiacName, year);
    }
}
