package com.porkolab.chinesezodiac.service;

import com.porkolab.chinesezodiac.entity.ZodiacCareer;
import com.porkolab.chinesezodiac.repository.ZodiacCareerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CareerService {

    private final ZodiacCareerRepository zodiacCareerRepository;

    public CareerService(ZodiacCareerRepository zodiacCareerRepository) {
        this.zodiacCareerRepository = zodiacCareerRepository;
    }

    public List<ZodiacCareer> getCareersByZodiacName(String zodiacName) {
        return zodiacCareerRepository.findByZodiacName(zodiacName);
    }
}
