package com.porkolab.chinesezodiac.repository;

import com.porkolab.chinesezodiac.entity.LifeStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LifeStageRepository extends JpaRepository<LifeStage, Long> {
    List<LifeStage> findByZodiacs_Name(String zodiacName);
}
