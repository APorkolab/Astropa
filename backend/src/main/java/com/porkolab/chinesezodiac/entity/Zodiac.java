package com.porkolab.chinesezodiac.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "zodiac")
public class Zodiac {
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "description")
    private String description;

    @ManyToMany
    @JoinTable(
            name = "zodiac_project",
            joinColumns = @JoinColumn(name = "zodiac_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> projects;

    @ManyToMany
    @JoinTable(
            name = "zodiac_life_stage",
            joinColumns = @JoinColumn(name = "zodiac_id"),
            inverseJoinColumns = @JoinColumn(name = "life_stage_id")
    )
    private Set<LifeStage> lifeStages;

    @ManyToMany
    @JoinTable(
            name = "zodiac_career",
            joinColumns = @JoinColumn(name = "zodiac_id"),
            inverseJoinColumns = @JoinColumn(name = "career_id")
    )
    private Set<Career> careers;

    @ManyToMany
    @JoinTable(
            name = "zodiac_personality",
            joinColumns = @JoinColumn(name = "zodiac_id"),
            inverseJoinColumns = @JoinColumn(name = "personality_id")
    )
    private Set<Personality> personalities;

    @ManyToMany
    @JoinTable(
            name = "zodiac_goal",
            joinColumns = @JoinColumn(name = "zodiac_id"),
            inverseJoinColumns = @JoinColumn(name = "goal_id")
    )
    private Set<Goal> goals;

    @ManyToMany
    @JoinTable(
            name = "zodiac_divination",
            joinColumns = @JoinColumn(name = "zodiac_id"),
            inverseJoinColumns = @JoinColumn(name = "divination_id")
    )
    private Set<Divination> divinations;

    public Zodiac() {
    }

    public Zodiac(String name, LocalDate startDate, LocalDate endDate, String description) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Set<LifeStage> getLifeStages() {
        return lifeStages;
    }

    public void setLifeStages(Set<LifeStage> lifeStages) {
        this.lifeStages = lifeStages;
    }

    public Set<Career> getCareers() {
        return careers;
    }

    public void setCareers(Set<Career> careers) {
        this.careers = careers;
    }

    public Set<Personality> getPersonalities() {
        return personalities;
    }

    public void setPersonalities(Set<Personality> personalities) {
        this.personalities = personalities;
    }

    public Set<Goal> getGoals() {
        return goals;
    }

    public void setGoals(Set<Goal> goals) {
        this.goals = goals;
    }

    public Set<Divination> getDivinations() {
        return divinations;
    }

    public void setDivinations(Set<Divination> divinations) {
        this.divinations = divinations;
    }
}
