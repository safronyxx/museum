package com.example.museum.repository;

import com.example.museum.model.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Репозиторий для управления сущностями "Экспонат" ({@link Exhibit}).
 * <p>
 * Содержит методы для поиска экспонатов по автору, эпохе или обоим критериям одновременно.
 */
@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {

    /**
     * Находит экспонаты, автор которых содержит указанную строку (регистронезависимо).
     *
     * @param author частичное или полное имя автора
     * @return список найденных экспонатов
     */
    List<Exhibit> findByAuthorContainingIgnoreCase(String author);

    /**
     * Находит экспонаты, эпоха которых содержит указанную строку (регистронезависимо).
     *
     * @param era частичное или полное название эпохи
     * @return список найденных экспонатов
     */
    List<Exhibit> findByEraContainingIgnoreCase(String era);

    /**
     * Находит экспонаты, соответствующие одновременно заданному автору и эпохе
     * (регистронезависимо).
     *
     * @param author частичное или полное имя автора
     * @param era    частичное или полное название эпохи
     * @return список найденных экспонатов
     */
    List<Exhibit> findByAuthorContainingIgnoreCaseAndEraContainingIgnoreCase(String author, String era);

}