package com.example.museum.repository;

import com.example.museum.model.Role;
import com.example.museum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Репозиторий для управления сущностями "Пользователь" ({@link User}).
 * <p>
 * Используется для проверки существования пользователя по email,
 * поиска по email и фильтрации по ролям.
 *
 * @author Костенко М.С.
 * @since 1.0
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Проверяет, существует ли пользователь с указанным email-адресом.
     *
     * @param email email пользователя
     * @return {@code true}, если пользователь существует, иначе {@code false}
     */
    boolean existsByEmail(String email);

    /**
     * Находит пользователя по email-адресу.
     *
     * @param email email пользователя
     * @return объект {@link Optional}, содержащий пользователя, если он найден
     */
    Optional<User> findByEmail(String email);

    /**
     * Возвращает список всех пользователей с заданной ролью.
     *
     * @param role роль пользователей (например, {@code Role.GUIDE})
     * @return список пользователей с указанной ролью
     */
    List<User> findByRole(Role role);
}