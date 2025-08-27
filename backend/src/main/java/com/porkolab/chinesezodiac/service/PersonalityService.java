package com.porkolab.chinesezodiac.service;

import com.porkolab.chinesezodiac.entity.ZodiacPersonality;
import com.porkolab.chinesezodiac.repository.ZodiacPersonalityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonalityService {

    private final ZodiacPersonalityRepository zodiacPersonalityRepository;

    public PersonalityService(ZodiacPersonalityRepository zodiacPersonalityRepository) {
        this.zodiacPersonalityRepository = zodiacPersonalityRepository;
    }

    public List<ZodiacPersonality> getPersonalitiesByZodiacName(String zodiacName) {
        return zodiacPersonalityRepository.findByZodiacName(zodiacName);
    }
}
