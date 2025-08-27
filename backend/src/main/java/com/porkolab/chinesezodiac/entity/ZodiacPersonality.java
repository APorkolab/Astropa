package com.porkolab.chinesezodiac.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "zodiac_personality")
public class ZodiacPersonality {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "zodiac_id", nullable = false)
    private Zodiac zodiac;

    @ManyToOne
    @JoinColumn(name = "personality_id", nullable = false)
    private Personality personality;

    public ZodiacPersonality() {
    }

    public ZodiacPersonality(Zodiac zodiac, Personality personality) {
        this.zodiac = zodiac;
        this.personality = personality;
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

    public Personality getPersonality() {
        return personality;
    }

    public void setPersonality(Personality personality) {
        this.personality = personality;
    }
}
