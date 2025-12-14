package com.example.museum.model;

import jakarta.persistence.*;

@Entity
@Table(name = "exhibition_exhibits")
public class ExhibitionExhibit {

    @EmbeddedId
    private ExhibitionExhibitId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("exhibitionId")
    @JoinColumn(name = "exhibition_id", nullable = false)
    private Exhibition exhibition;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("exhibitId")
    @JoinColumn(name = "exhibit_id", nullable = false)
    private Exhibit exhibit;

    public ExhibitionExhibit() {}

    public ExhibitionExhibit(Exhibition exhibition, Exhibit exhibit) {
        this.exhibition = exhibition;
        this.exhibit = exhibit;
        this.id = new ExhibitionExhibitId(exhibition.getId(), exhibit.getId());
    }

    public ExhibitionExhibitId getId() {
        return id;
    }

    public void setId(ExhibitionExhibitId id) {
        this.id = id;
    }

    public Exhibition getExhibition() {
        return exhibition;
    }

    public void setExhibition(Exhibition exhibition) {
        this.exhibition = exhibition;
        if (this.id == null) this.id = new ExhibitionExhibitId();
        this.id.setExhibitionId(exhibition != null ? exhibition.getId() : null);
    }

    public Exhibit getExhibit() {
        return exhibit;
    }

    public void setExhibit(Exhibit exhibit) {
        this.exhibit = exhibit;
        if (this.id == null) this.id = new ExhibitionExhibitId();
        this.id.setExhibitId(exhibit != null ? exhibit.getId() : null);
    }
}