package com.porkolab.chinesezodiac.repository;

import com.porkolab.chinesezodiac.entity.ZodiacCareer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ZodiacCareerRepository extends JpaRepository<ZodiacCareer, Long> {
    List<ZodiacCareer> findByZodiacName(String zodiacName);
}
