package com.porkolab.chinesezodiac.service;

import com.porkolab.chinesezodiac.entity.Project;
import com.porkolab.chinesezodiac.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<Project> getProjectsByZodiacName(String zodiacName) {
        return projectRepository.findByZodiacs_Name(zodiacName);
    }
}
