package com.example.museum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exhibits")
public class Exhibit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "creation_year", nullable = false)
    private Integer creationYear;

    @Column(name = "era", nullable = false)
    private String era;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hall_id", nullable = false, foreignKey = @ForeignKey(name = "fk_exhibit_hall"))
    private Hall hall;

    public Exhibit() {}
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