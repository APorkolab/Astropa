package com.porkolab.chinesezodiac.repository;

import com.porkolab.chinesezodiac.entity.ZodiacPersonality;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ZodiacPersonalityRepository extends JpaRepository<ZodiacPersonality, Long> {
    List<ZodiacPersonality> findByZodiacName(String zodiacName);
}
