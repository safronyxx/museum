package com.example.museum.service;

import com.example.museum.model.Exhibit;
import java.util.List;

public interface ExhibitService {
    List<Exhibit> findAll();
    Exhibit findById(Long id);
    Exhibit save(Exhibit exhibit);
    void deleteById(Long id);
    List<Exhibit> searchByAuthorAndEra(String author, String era);
}