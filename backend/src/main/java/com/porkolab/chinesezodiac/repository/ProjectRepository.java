package com.porkolab.chinesezodiac.repository;

import com.porkolab.chinesezodiac.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByZodiacs_Name(String zodiacName);
}
