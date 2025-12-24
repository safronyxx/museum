package com.example.museum.model;

import jakarta.persistence.*;


/**
 * Сущность, представляющая зал музея.
 * <p>
 * Соответствует таблице {@code halls} и содержит информацию о названии,
 * этаже, вместимости и описании зала.
 */
@Entity
@Table(name = "halls")
public class Hall {

    /**
     * Уникальный идентификатор зала.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название зала.
     */
    @Column(nullable = false)
    private String name;

    /**
     * Этаж, на котором расположен зал.
     */
    @Column(nullable = false)
    private Integer floor;

    /**
     * Вместимость зала (максимальное количество посетителей).
     */
    @Column(nullable = false)
    private Integer capacity;

    /**
     * Описание зала (опционально).
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    public Hall() {}

    /**
     * Конструктор для создания зала с заданными параметрами.
     *
     * @param name        название зала
     * @param floor       этаж
     * @param capacity    вместимость
     * @param description описание
     */
    public Hall(String name, Integer floor, Integer capacity, String description) {
        this.name = name;
        this.floor = floor;
        this.capacity = capacity;
        this.description = description;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Integer getFloor() { return floor; }
    public void setFloor(Integer floor) { this.floor = floor; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}