package com.porkolab.chinesezodiac.controller;

import com.porkolab.chinesezodiac.entity.Project;
import com.porkolab.chinesezodiac.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "http://localhost:4200")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/{zodiacName}")
    public List<Project> getProjectsByZodiacName(@PathVariable String zodiacName) {
        return projectService.getProjectsByZodiacName(zodiacName);
    }
}
