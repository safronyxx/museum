package com.example.museum.service;

import com.example.museum.model.Exhibition;
import com.example.museum.repository.ExhibitionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExhibitionServiceImpl implements ExhibitionService {

    private final ExhibitionRepository exhibitionRepository;

    public ExhibitionServiceImpl(ExhibitionRepository exhibitionRepository) {
        this.exhibitionRepository = exhibitionRepository;
    }

    @Override
    public List<Exhibition> findAll() {
        return exhibitionRepository.findAll();
    }

    @Override
    public Exhibition findById(Long id) {
        return exhibitionRepository.findById(id).orElse(null);
    }

    @Override
    public Exhibition save(Exhibition exhibition) {
        return exhibitionRepository.save(exhibition);
    }

    @Override
    public void deleteById(Long id) {
        exhibitionRepository.deleteById(id);
    }

    @Override
    public List<Exhibition> findByCuratorEmail(String curatorEmail) {
        return exhibitionRepository.findByCuratorEmail(curatorEmail);
    }
}