package com.porkolab.chinesezodiac.service;

import com.porkolab.chinesezodiac.entity.LifeStage;
import com.porkolab.chinesezodiac.repository.LifeStageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LifeStageService {

    private final LifeStageRepository lifeStageRepository;

    public LifeStageService(LifeStageRepository lifeStageRepository) {
        this.lifeStageRepository = lifeStageRepository;
    }

    public List<LifeStage> getLifeStagesByZodiacName(String zodiacName) {
        return lifeStageRepository.findByZodiacs_Name(zodiacName);
    }
}
