package com.porkolab.chinesezodiac.service;

import com.porkolab.chinesezodiac.entity.Zodiac;
import com.porkolab.chinesezodiac.exception.ResourceNotFoundException;
import com.porkolab.chinesezodiac.repository.ZodiacRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ZodiacService {

    private final ZodiacRepository zodiacRepository;

    public ZodiacService(ZodiacRepository zodiacRepository) {
        this.zodiacRepository = zodiacRepository;
    }

    public List<Zodiac> findAll() {
        return zodiacRepository.findAll();
    }

    public Optional<Zodiac> findById(Long id) {
        return zodiacRepository.findById(id);
    }

    public Zodiac save(Zodiac zodiac) {
        return zodiacRepository.save(zodiac);
    }

    public void deleteById(Long id) {
        zodiacRepository.deleteById(id);
    }

    public Zodiac findByName(String name) {
        return zodiacRepository.findByName(name);
    }

    public List<Zodiac> findByDate(LocalDate date) {
        List<Zodiac> zodiacs = zodiacRepository.findByDate(date);
        if (zodiacs.isEmpty()) {
            throw new ResourceNotFoundException("Zodiac not found for date: " + date);
        }
        return zodiacs;
    }

    public Zodiac update(Long id, Zodiac zodiac) {
        return findById(id).map(existingZodiac -> {
            existingZodiac.setName(zodiac.getName());
            existingZodiac.setStartDate(zodiac.getStartDate());
            existingZodiac.setEndDate(zodiac.getEndDate());
            return save(existingZodiac);
        }).orElseThrow(() -> new ResourceNotFoundException("Zodiac not found with id: " + id));
    }
}