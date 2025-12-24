package com.example.museum.service;

import com.example.museum.model.Exhibition;
import java.util.List;


/**
 * Интерфейс сервиса для управления выставками.
 * <p>
 * Определяет контракт для выполнения операций CRUD и поиска выставок
 * по email-адресу куратора.
 */
public interface ExhibitionService {

    /**
     * Возвращает список всех выставок.
     *
     * @return список выставок
     */
    List<Exhibition> findAll();

    /**
     * Находит выставку по её уникальному идентификатору.
     *
     * @param id идентификатор выставки
     * @return объект выставки или {@code null}, если не найдена
     */
    Exhibition findById(Long id);

    /**
     * Сохраняет или обновляет выставку в базе данных.
     *
     * @param exhibition объект выставки для сохранения
     * @return сохранённая выставка
     */
    Exhibition save(Exhibition exhibition);

    /**
     * Удаляет выставку по идентификатору.
     *
     * @param id идентификатор выставки
     */
    void deleteById(Long id);

    /**
     * Возвращает список выставок, куратором которых является пользователь
     * с указанным email-адресом.
     *
     * @param curatorEmail email куратора (гида)
     * @return список выставок
     */
    List<Exhibition> findByCuratorEmail(String curatorEmail);
}