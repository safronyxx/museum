package com.example.museum.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;


/**
 * Сущность, представляющая факт посещения выставки.
 * <p>
 * Соответствует таблице {@code visits} и фиксирует email посетителя,
 * дату регистрации и ссылку на выставку.
 */
@Entity
@Table(name = "visits")
public class Visit {

    /**
     * Уникальный идентификатор записи о посещении.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Email посетителя (не является внешним ключом, так как посетители могут быть не зарегистрированы).
     */
    @Column(name = "visitor_email", nullable = false)
    private String visitorEmail;

    /**
     * Дата и время регистрации посещения.
     */
    @Column(name = "visit_date", nullable = false)
    private LocalDateTime visitDate;

    /**
     * Выставка, на которую зарегистрировано посещение.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id", nullable = false, foreignKey = @ForeignKey(name = "fk_visit_exhibition"))
    private Exhibition exhibition;

    /**
     * Конструктор по умолчанию, устанавливает текущую дату как время регистрации.
     */
    public Visit() {
        this.visitDate = LocalDateTime.now();
    }

    /**
     * Конструктор для создания записи о посещении.
     *
     * @param visitorEmail email посетителя
     * @param exhibition   выставка
     */
    public Visit(String visitorEmail, Exhibition exhibition) {
        this.visitorEmail = visitorEmail;
        this.exhibition = exhibition;
        this.visitDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVisitorEmail() { return visitorEmail; }
    public void setVisitorEmail(String visitorEmail) { this.visitorEmail = visitorEmail; }

    public LocalDateTime getVisitDate() { return visitDate; }
    public void setVisitDate(LocalDateTime visitDate) { this.visitDate = visitDate; }

    public Exhibition getExhibition() { return exhibition; }
    public void setExhibition(Exhibition exhibition) { this.exhibition = exhibition; }
}