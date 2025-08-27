package com.porkolab.chinesezodiac.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToMany;
import java.util.Set;

@Entity
@Table(name = "personality")
public class Personality {
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "personalities")
    private Set<Zodiac> zodiacs;

    public Personality() {
    }

    public Personality(String name, String description) {
        this.name = name;
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
