package com.example.museum.service;

import com.example.museum.model.Hall;
import java.util.List;

public interface HallService {
    List<Hall> findAll();
    Hall findById(Long id);
    Hall save(Hall hall);
    void deleteById(Long id);
    List<Hall> searchByNameAndFloor(String name, Integer floor);
}