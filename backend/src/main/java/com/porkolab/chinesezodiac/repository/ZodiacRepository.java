package com.porkolab.chinesezodiac.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import com.porkolab.chinesezodiac.entity.Zodiac;


@Repository
public interface ZodiacRepository extends JpaRepository<Zodiac, Long> {
    @Query("SELECT z FROM Zodiac z WHERE z.startDate <= :date AND z.endDate >= :date")
    List<Zodiac> findByDate(@Param("date") LocalDate date);
    Zodiac findByName(String name);
}