package com.porkolab.chinesezodiac.repository;

import com.porkolab.chinesezodiac.entity.Divination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DivinationRepository extends JpaRepository<Divination, Long> {
    List<Divination> findByZodiacs_Name(String zodiacName);
}
