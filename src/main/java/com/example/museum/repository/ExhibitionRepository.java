package com.example.museum.repository;

import com.example.museum.model.Exhibition;
import com.example.museum.model.Role;
import com.example.museum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Репозиторий для управления сущностями "Выставка" ({@link Exhibition}).
 * <p>
 * Предоставляет методы для поиска выставок по куратору, подсчёта количества
 * выставок с одинаковым названием и агрегации данных по гидам.
 */
@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {

    /**
     * Подсчитывает количество выставок с указанным названием.
     * Используется для предотвращения дублирования постоянных выставок.
     *
     * @param title название выставки
     * @return количество выставок с данным названием
     */
    long countByTitle(String title);

    /**
     * Находит все выставки, куратором которых является пользователь
     * с указанным email-адресом.
     *
     * @param email email куратора (гида)
     * @return список выставок, привязанных к данному гиду
     */
    @Query("SELECT e FROM Exhibition e WHERE e.curator.email = :email")
    List<Exhibition> findByCuratorEmail(@Param("email") String email);

    /**
     * Возвращает статистику: количество выставок, сгруппированных по email'ам гидов.
     * Результат представляет собой список массивов объектов, где
     * первый элемент — email гида, второй — количество его выставок.
     *
     * @return список массивов вида [email, количество_выставок]
     */
    @Query("SELECT e.curator.email, COUNT(e) FROM Exhibition e WHERE e.curator IS NOT NULL GROUP BY e.curator.email")
    List<Object[]> countExhibitionsByCuratorEmail();
}