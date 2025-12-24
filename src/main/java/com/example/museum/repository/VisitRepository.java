package com.example.museum.repository;

import com.example.museum.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Репозиторий для управления сущностями "Посещение" ({@link Visit}).
 * <p>
 * Обеспечивает методы для получения списка посещений с сортировкой по названию выставки,
 * а также подсчёта количества посещений конкретной выставки.
 */
@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {


    /**
     * Возвращает все посещения, отсортированные по названию выставки по возрастанию.
     *
     * @return список посещений
     */
    @Query("SELECT v FROM Visit v JOIN v.exhibition e ORDER BY e.title ASC")
    List<Visit> findAllByExhibitionTitleAsc();


    /**
     * Возвращает все посещения, отсортированные по названию выставки по убыванию.
     *
     * @return список посещений
     */
    @Query("SELECT v FROM Visit v JOIN v.exhibition e ORDER BY e.title DESC")
    List<Visit> findAllByExhibitionTitleDesc();


    /**
     * Возвращает все посещения определённого пользователя, отсортированные по названию выставки по возрастанию.
     *
     * @param email email посетителя
     * @return список посещений пользователя
     */
    @Query("SELECT v FROM Visit v JOIN v.exhibition e WHERE v.visitorEmail = :email ORDER BY e.title ASC")
    List<Visit> findByVisitorEmailOrderByExhibitionTitle(@Param("email") String email);


    /**
     * Возвращает все посещения определённого пользователя, отсортированные по названию выставки по убыванию.
     *
     * @param email email посетителя
     * @return список посещений пользователя
     */
    @Query("SELECT v FROM Visit v JOIN v.exhibition e WHERE v.visitorEmail = :email ORDER BY e.title DESC")
    List<Visit> findByVisitorEmailOrderByExhibitionTitleDesc(@Param("email") String email);


    /**
     * Подсчитывает общее количество посещений заданной выставки.
     *
     * @param exhibitionId идентификатор выставки
     * @return количество записей о посещении
     */
    @Query("SELECT COUNT(v) FROM Visit v WHERE v.exhibition.id = :exhibitionId")
    long countByExhibitionId(@Param("exhibitionId") Long exhibitionId);

}
