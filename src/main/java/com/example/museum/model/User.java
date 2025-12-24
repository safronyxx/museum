package com.example.museum.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;


/**
 * Сущность, представляющая пользователя системы.
 * <p>
 * Соответствует таблице {@code users} и содержит данные для аутентификации,
 * роль и полное имя.
 */
@Entity
@Table(name = "users")
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email пользователя, используется в качестве логина (уникален).
     */
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Хэш пароля, зашифрованный с использованием BCrypt.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Роль пользователя в системе.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Полное имя пользователя.
     */
    @Column(name = "full_name", nullable = false)
    private String fullName;

    /**
     * Дата и время регистрации пользователя.
     * Поле заполняется автоматически при создании записи.
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;


    public User() {}

    /**
     * Конструктор для создания пользователя с заданными параметрами.
     *
     * @param email     email (логин)
     * @param password  пароль в открытом виде (будет зашифрован отдельно)
     * @param role      роль пользователя
     * @param fullName  полное имя
     */
    public User(String email, String password, Role role, String fullName) {
        this.email = email;
        this.password = password;
        this.role = role;
        this.fullName = fullName;
        this.createdAt = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}