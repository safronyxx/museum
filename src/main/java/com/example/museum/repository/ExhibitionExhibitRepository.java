package com.example.museum.repository;

import com.example.museum.model.ExhibitionExhibit;
import com.example.museum.model.ExhibitionExhibitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExhibitionExhibitRepository extends JpaRepository<ExhibitionExhibit, ExhibitionExhibitId> {
}