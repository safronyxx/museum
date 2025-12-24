package com.example.museum.service;

import com.example.museum.model.Hall;
import com.example.museum.repository.HallRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Реализация сервиса {@link HallService}.
 * <p>
 * Реализует логику поиска залов с поддержкой частичного совпадения по названию
 * и точного совпадения по этажу.
 */
@Service
@Transactional
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;

    public HallServiceImpl(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    @Override
    public List<Hall> findAll() {
        return hallRepository.findAll();
    }

    @Override
    public Hall findById(Long id) {
        return hallRepository.findById(id).orElse(null);
    }

    @Override
    public Hall save(Hall hall) {
        return hallRepository.save(hall);
    }

    @Override
    public void deleteById(Long id) {
        hallRepository.deleteById(id);
    }

    @Override
    public List<Hall> searchByNameAndFloor(String name, Integer floor) {
        String cleanName = (name != null) ? name.trim() : "";
        boolean hasName = !cleanName.isEmpty();
        boolean hasFloor = floor != null;

        if (!hasName && !hasFloor) {
            return findAll();
        } else if (!hasName) {
            return hallRepository.findByFloor(floor);
        } else if (!hasFloor) {
            return hallRepository.findByNameContainingIgnoreCase(cleanName);
        } else {
            return hallRepository.findByNameContainingIgnoreCaseAndFloor(cleanName, floor);
        }
    }
}