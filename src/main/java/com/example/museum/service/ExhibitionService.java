package com.example.museum.service;

import com.example.museum.model.Exhibition;
import java.util.List;

public interface ExhibitionService {
    List<Exhibition> findAll();
    Exhibition findById(Long id);
    Exhibition save(Exhibition exhibition);
    void deleteById(Long id);
    List<Exhibition> findByCuratorEmail(String curatorEmail);
}