package com.example.museum.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "exhibitions")
public class Exhibition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curator_email", referencedColumnName = "email")
    private User curator;

    public Exhibition() {}
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