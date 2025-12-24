package com.example.museum.service;

import com.example.museum.model.Exhibit;
import java.util.List;


/**
 * Интерфейс сервиса для управления экспонатами.
 * <p>
 * Предоставляет методы для выполнения операций CRUD и поиска экспонатов
 * по автору и эпохе.
 */
public interface ExhibitService {
    List<Exhibit> findAll();
    Exhibit findById(Long id);
    Exhibit save(Exhibit exhibit);
    void deleteById(Long id);
    List<Exhibit> searchByAuthorAndEra(String author, String era);
}