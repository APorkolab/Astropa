package com.porkolab.chinesezodiac.repository;

import com.porkolab.chinesezodiac.entity.Personality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalityRepository extends JpaRepository<Personality, Long> {
}
