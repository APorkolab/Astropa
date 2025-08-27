package com.porkolab.chinesezodiac.repository;

import com.porkolab.chinesezodiac.entity.Compatibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CompatibilityRepository extends JpaRepository<Compatibility, Long> {
    List<Compatibility> findByZodiac1NameAndZodiac2Name(String zodiac1Name, String zodiac2Name);
}
