package com.example.museum.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * Сущность, представляющая выставку в музее.
 * <p>
 * Соответствует таблице {@code exhibitions} и содержит информацию
 * о названии, датах начала и окончания, описании и кураторе выставки.
 */
@Entity
@Table(name = "exhibitions")
public class Exhibition {

    /**
     * Уникальный идентификатор выставки.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название выставки.
     */
    @Column(name = "title", nullable = false)
    private String title;

    /**
     * Дата начала выставки.
     */
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    /**
     * Дата окончания выставки.
     */
    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    /**
     * Описание выставки (опционально).
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Куратор выставки — пользователь с ролью {@link Role#GUIDE}.
     * Связь реализована по email-адресу, который является уникальным ключом в таблице {@code users}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curator_email", referencedColumnName = "email")
    private User curator;

    public Exhibition() {}

    /**
     * Конструктор для создания выставки с заданными параметрами.
     *
     * @param title       название выставки
     * @param startDate   дата начала
     * @param endDate     дата окончания
     * @param curator     куратор (пользователь-гид)
     * @param description описание (может быть {@code null})
     */
    public Exhibition(String title, LocalDate startDate, LocalDate endDate, User curator, String description) {
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.curator = curator;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public User getCurator() { return curator; }
    public void setCurator(User curator) { this.curator = curator; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}