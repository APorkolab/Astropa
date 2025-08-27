package com.porkolab.chinesezodiac.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "zodiac_career")
public class ZodiacCareer {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "zodiac_id", nullable = false)
    private Zodiac zodiac;

    @ManyToOne
    @JoinColumn(name = "career_id", nullable = false)
    private Career career;

    public ZodiacCareer() {
    }

    public ZodiacCareer(Zodiac zodiac, Career career) {
        this.zodiac = zodiac;
        this.career = career;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Zodiac getZodiac() {
        return zodiac;
    }

    public void setZodiac(Zodiac zodiac) {
        this.zodiac = zodiac;
    }

    public Career getCareer() {
        return career;
    }

    public void setCareer(Career career) {
        this.career = career;
    }
}
