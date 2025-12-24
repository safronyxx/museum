package com.example.museum.repository;

import com.example.museum.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Репозиторий для управления сущностями "Зал" ({@link Hall}).
 * <p>
 * Обеспечивает методы поиска залов по названию, этажу или обоим параметрам.
 *
 * @author Костенко М.С.
 * @since 1.0
 */
@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {

    /**
     * Находит залы, название которых содержит указанную строку (регистронезависимо).
     *
     * @param name частичное или полное название зала
     * @return список найденных залов
     */
    List<Hall> findByNameContainingIgnoreCase(String name);

    /**
     * Находит все залы, расположенные на указанном этаже.
     *
     * @param floor номер этажа
     * @return список залов на заданном этаже
     */
    List<Hall> findByFloor(Integer floor);

    /**
     * Находит залы, соответствующие одновременно заданному названию и этажу.
     *
     * @param name  частичное или полное название зала
     * @param floor номер этажа
     * @return список залов, соответствующих обоим критериям
     */
    List<Hall> findByNameContainingIgnoreCaseAndFloor(String name, Integer floor);
}