package com.porkolab.chinesezodiac.service;

import com.porkolab.chinesezodiac.entity.Goal;
import com.porkolab.chinesezodiac.repository.GoalRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    private final GoalRepository goalRepository;

    public GoalService(GoalRepository goalRepository) {
        this.goalRepository = goalRepository;
    }

    public List<Goal> getGoalsByZodiacName(String zodiacName) {
        return goalRepository.findByZodiacs_Name(zodiacName);
    }
}
