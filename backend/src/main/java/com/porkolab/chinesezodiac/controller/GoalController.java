package com.porkolab.chinesezodiac.controller;

import com.porkolab.chinesezodiac.entity.Goal;
import com.porkolab.chinesezodiac.service.GoalService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/goals")
@CrossOrigin(origins = "http://localhost:4200")
public class GoalController {

    private final GoalService goalService;

    public GoalController(GoalService goalService) {
        this.goalService = goalService;
    }

    @GetMapping("/{zodiacName}")
    public List<Goal> getGoalsByZodiacName(@PathVariable String zodiacName) {
        return goalService.getGoalsByZodiacName(zodiacName);
    }
}
