package com.example.museum.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ExhibitionExhibitId implements Serializable {

    private Long exhibitionId;
    private Long exhibitId;

    public ExhibitionExhibitId() {}

    public ExhibitionExhibitId(Long exhibitionId, Long exhibitId) {
        this.exhibitionId = exhibitionId;
        this.exhibitId = exhibitId;
    }

    public Long getExhibitionId() {
        return exhibitionId;
    }

    public void setExhibitionId(Long exhibitionId) {
        this.exhibitionId = exhibitionId;
    }

    public Long getExhibitId() {
        return exhibitId;
    }

    public void setExhibitId(Long exhibitId) {
        this.exhibitId = exhibitId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExhibitionExhibitId)) return false;
        ExhibitionExhibitId that = (ExhibitionExhibitId) o;
        return Objects.equals(exhibitionId, that.exhibitionId) &&
                Objects.equals(exhibitId, that.exhibitId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(exhibitionId, exhibitId);
    }
}