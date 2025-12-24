package com.example.museum.model;

import jakarta.persistence.*;


/**
 * Сущность, представляющая экспонат музея.
 * <p>
 * Соответствует таблице {@code exhibits} в базе данных и содержит информацию
 * о названии, описании, авторе, годе создания и эпохе экспоната,
 * а также ссылку на зал, в котором он находится.
 */
@Entity
@Table(name = "exhibits")
public class Exhibit {

    /**
     * Уникальный идентификатор экспоната.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Название экспоната.
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * Описание экспоната (опционально).
     */
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    /**
     * Автор экспоната.
     */
    @Column(name = "author", nullable = false)
    private String author;

    /**
     * Год создания экспоната.
     */
    @Column(name = "creation_year", nullable = false)
    private Integer creationYear;

    /**
     * Историческая эпоха, к которой относится экспонат.
     */
    @Column(name = "era", nullable = false)
    private String era;

    /**
     * Зал, в котором находится экспонат.
     * Установлена связь «многие к одному» с сущностью {@link Hall}.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false, foreignKey = @ForeignKey(name = "fk_exhibit_hall"))
    private Hall hall;

    /**
     * Пустой конструктор, необходимый для JPA.
     */
    public Exhibit() {}

    /**
     * Конструктор для создания экспоната с заданными параметрами.
     *
     * @param name          название экспоната
     * @param description   описание (может быть {@code null})
     * @param author        автор
     * @param creationYear  год создания
     * @param era           историческая эпоха
     * @param hall          зал, в котором размещён экспонат
     */
    public Exhibit(String name, String description, String author, Integer creationYear, String era, Hall hall) {
        this.name = name;
        this.description = description;
        this.author = author;
        this.creationYear = creationYear;
        this.era = era;
        this.hall = hall;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public Integer getCreationYear() { return creationYear; }
    public void setCreationYear(Integer creationYear) { this.creationYear = creationYear; }

    public String getEra() { return era; }
    public void setEra(String era) { this.era = era; }

    public Hall getHall() { return hall; }
    public void setHall(Hall hall) { this.hall = hall; }
}