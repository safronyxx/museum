package com.example.museum.service;

import com.example.museum.model.Visit;
import java.util.List;

public interface VisitService {
    List<Visit> findAll();
    Visit findById(Long id);
    Visit save(Visit visit);
    void deleteById(Long id);
    List<Visit> findAllSortedByExhibitionTitle(String sortDirection);
    List<Visit> findVisitsByVisitorEmail(String email, String sortDirection);

}