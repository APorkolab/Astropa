package com.porkolab.chinesezodiac.service;

import com.porkolab.chinesezodiac.entity.Divination;
import com.porkolab.chinesezodiac.repository.DivinationRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DivinationService {

    private final DivinationRepository divinationRepository;

    public DivinationService(DivinationRepository divinationRepository) {
        this.divinationRepository = divinationRepository;
    }

    public List<Divination> getDivinationsByZodiacName(String zodiacName) {
        return divinationRepository.findByZodiacs_Name(zodiacName);
    }
}
