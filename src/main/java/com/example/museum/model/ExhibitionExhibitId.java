package com.example.museum.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;


/**
 * Класс составного первичного ключа для сущности {@link ExhibitionExhibit}.
 * <p>
 * Используется для реализации связи «многие ко многим» между выставками и экспонатами.
 */
@Embeddable
public class ExhibitionExhibitId implements Serializable {

    /**
     * Идентификатор выставки.
     */
    private Long exhibitionId;

    /**
     * Идентификатор экспоната.
     */
    private Long exhibitId;

    public ExhibitionExhibitId() {}

    /**
     * Конструктор для инициализации составного ключа.
     *
     * @param exhibitionId идентификатор выставки
     * @param exhibitId    идентификатор экспоната
     */
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