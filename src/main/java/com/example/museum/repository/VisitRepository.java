package com.example.museum.repository;

import com.example.museum.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("SELECT v FROM Visit v JOIN v.exhibition e ORDER BY e.title ASC")
    List<Visit> findAllByExhibitionTitleAsc();

    @Query("SELECT v FROM Visit v JOIN v.exhibition e ORDER BY e.title DESC")
    List<Visit> findAllByExhibitionTitleDesc();

    @Query("SELECT v FROM Visit v JOIN v.exhibition e WHERE v.visitorEmail = :email ORDER BY e.title ASC")
    List<Visit> findByVisitorEmailOrderByExhibitionTitle(@Param("email") String email);

    @Query("SELECT v FROM Visit v JOIN v.exhibition e WHERE v.visitorEmail = :email ORDER BY e.title DESC")
    List<Visit> findByVisitorEmailOrderByExhibitionTitleDesc(@Param("email") String email);

    @Query("SELECT COUNT(v) FROM Visit v WHERE v.exhibition.id = :exhibitionId")
    long countByExhibitionId(@Param("exhibitionId") Long exhibitionId);

}
