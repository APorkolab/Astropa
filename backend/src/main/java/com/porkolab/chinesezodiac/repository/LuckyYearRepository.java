package com.porkolab.chinesezodiac.repository;

import com.porkolab.chinesezodiac.entity.LuckyYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LuckyYearRepository extends JpaRepository<LuckyYear, Long> {
    List<LuckyYear> findByZodiacNameAndYear(String zodiacName, int year);
}
