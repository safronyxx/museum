package com.example.museum.service;

import com.example.museum.model.Hall;
import java.util.List;


/**
 * Интерфейс сервиса для управления залами музея.
 * <p>
 * Обеспечивает доступ к операциям CRUD и поиск залов по названию и этажу.
 */
public interface HallService {
    List<Hall> findAll();
    Hall findById(Long id);
    Hall save(Hall hall);
    void deleteById(Long id);
    List<Hall> searchByNameAndFloor(String name, Integer floor);
}