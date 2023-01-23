package com.porkolab.chinesezodiac.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.porkolab.chinesezodiac.entity.Zodiac;
import com.porkolab.chinesezodiac.reposítory.ZodiacRepository;



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

    public List<Zodiac> findByDate(Date date) {
        return zodiacRepository.findByDate(date);
    }

    public Zodiac update(Long id, Zodiac zodiac) {
        Optional<Zodiac> currentZodiac = findById(id);
        if(currentZodiac.isPresent()){
            Zodiac updatedZodiac = currentZodiac.get();
            updatedZodiac.setName(zodiac.getName());
            updatedZodiac.setStartDate(zodiac.getStartDate());
            updatedZodiac.setEndDate(zodiac.getEndDate());
            return save(updatedZodiac);
        }else {
            throw new IllegalArgumentException("Zodiac not found for given id");
        }
    }
}