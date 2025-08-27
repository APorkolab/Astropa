package com.porkolab.chinesezodiac.repository;

import com.porkolab.chinesezodiac.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GoalRepository extends JpaRepository<Goal, Long> {
    List<Goal> findByZodiacs_Name(String zodiacName);
}
