package com.example.museum.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visits")
public class Visit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visitor_email", nullable = false)
    private String visitorEmail;

    @Column(name = "visit_date", nullable = false)
    private LocalDateTime visitDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exhibition_id", nullable = false, foreignKey = @ForeignKey(name = "fk_visit_exhibition"))
    private Exhibition exhibition;

    public Visit() {
        this.visitDate = LocalDateTime.now();
    }
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