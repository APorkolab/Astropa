package com.porkolab.chinesezodiac.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "compatibility")
public class Compatibility {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "zodiac1_id", nullable = false)
    private Zodiac zodiac1;

    @ManyToOne
    @JoinColumn(name = "zodiac2_id", nullable = false)
    private Zodiac zodiac2;

    @Column(name = "compatibility_type", nullable = false)
    private String compatibilityType;

    @Column(name = "description")
    private String description;

    public Compatibility() {
    }

    public Compatibility(Zodiac zodiac1, Zodiac zodiac2, String compatibilityType, String description) {
        this.zodiac1 = zodiac1;
        this.zodiac2 = zodiac2;
        this.compatibilityType = compatibilityType;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Zodiac getZodiac1() {
        return zodiac1;
    }

    public void setZodiac1(Zodiac zodiac1) {
        this.zodiac1 = zodiac1;
    }

    public Zodiac getZodiac2() {
        return zodiac2;
    }

    public void setZodiac2(Zodiac zodiac2) {
        this.zodiac2 = zodiac2;
    }

    public String getCompatibilityType() {
        return compatibilityType;
    }

    public void setCompatibilityType(String compatibilityType) {
        this.compatibilityType = compatibilityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
