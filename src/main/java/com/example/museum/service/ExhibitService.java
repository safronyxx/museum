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

    /**
     * Возвращает список всех экспонатов.
     *
     * @return список экспонатов
     */
    List<Exhibit> findAll();

    /**
     * Находит экспонат по его уникальному идентификатору.
     *
     * @param id идентификатор экспоната
     * @return объект экспоната или {@code null}, если не найден
     */
    Exhibit findById(Long id);

    /**
     * Сохраняет или обновляет экспонат в базе данных.
     *
     * @param exhibit объект экспоната для сохранения
     * @return сохраненный экспонат
     */
    Exhibit save(Exhibit exhibit);

    /**
     * Удаляет экспонат по идентификатору.
     *
     * @param id идентификатор экспоната
     */
    void deleteById(Long id);

    /**
     * Возвращает список экспонатов по автору и исторической эпохе
     *
     * @param author имя автора
     * @param era историческая эпоха
     * @return список экспонатов
     */
    List<Exhibit> searchByAuthorAndEra(String author, String era);
}