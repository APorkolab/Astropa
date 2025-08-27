package com.porkolab.chinesezodiac.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "goal")
public class Goal {

    @Id
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;

    @ManyToMany(mappedBy = "goals")
    @JsonIgnore
    private Set<Zodiac> zodiacs;

    // Getters and setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Zodiac> getZodiacs() {
        return zodiacs;
    }

    public void setZodiacs(Set<Zodiac> zodiacs) {
        this.zodiacs = zodiacs;
    }
}
