package com.porkolab.chinesezodiac.service;

import com.porkolab.chinesezodiac.entity.Compatibility;
import com.porkolab.chinesezodiac.repository.CompatibilityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompatibilityService {

    private final CompatibilityRepository compatibilityRepository;

    public CompatibilityService(CompatibilityRepository compatibilityRepository) {
        this.compatibilityRepository = compatibilityRepository;
    }

    public List<Compatibility> getCompatibility(String zodiac1Name, String zodiac2Name) {
        return compatibilityRepository.findByZodiac1NameAndZodiac2Name(zodiac1Name, zodiac2Name);
    }
}
