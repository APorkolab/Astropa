package com.porkolab.chinesezodiac.reposítory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Date;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.porkolab.chinesezodiac.entity.Zodiac;


public interface ZodiacRepository extends JpaRepository<Zodiac, Long> {
    @Query("SELECT z FROM Zodiac z WHERE z.startDate <= :date AND z.endDate >= :date")
    List<Zodiac> findByDate(@Param("date") Date date);
    Zodiac findByName(String name);
}