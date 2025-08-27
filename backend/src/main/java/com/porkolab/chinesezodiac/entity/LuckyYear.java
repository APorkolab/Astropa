package com.porkolab.chinesezodiac.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
@Table(name = "lucky_year")
public class LuckyYear {
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "zodiac_id", nullable = false)
    private Zodiac zodiac;

    @Column(name = "lucky_year", nullable = false)
    private int year;

    @Column(name = "is_lucky", nullable = false)
    private boolean isLucky;

    @Column(name = "description")
    private String description;

    public LuckyYear() {
    }

    public LuckyYear(Zodiac zodiac, int year, boolean isLucky, String description) {
        this.zodiac = zodiac;
        this.year = year;
        this.isLucky = isLucky;
        this.description = description;
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

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isLucky() {
        return isLucky;
    }

    public void setLucky(boolean lucky) {
        isLucky = lucky;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
