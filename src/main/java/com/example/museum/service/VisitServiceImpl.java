package com.example.museum.service;

import com.example.museum.model.Visit;
import com.example.museum.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class VisitServiceImpl implements VisitService {

    private final VisitRepository visitRepository;

    public VisitServiceImpl(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public List<Visit> findAll() {
        return visitRepository.findAll();
    }

    @Override
    public Visit findById(Long id) {
        return visitRepository.findById(id).orElse(null);
    }

    @Override
    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }

    @Override
    public List<Visit> findAllSortedByExhibitionTitle(String sortDirection) {
        if ("desc".equalsIgnoreCase(sortDirection)) {
            return visitRepository.findAllByExhibitionTitleDesc();
        } else {
            return visitRepository.findAllByExhibitionTitleAsc();
        }
    }

    @Override
    public List<Visit> findVisitsByVisitorEmail(String email, String sortDirection) {
        if ("desc".equalsIgnoreCase(sortDirection)) {
            return visitRepository.findByVisitorEmailOrderByExhibitionTitleDesc(email);
        } else {
            return visitRepository.findByVisitorEmailOrderByExhibitionTitle(email);
        }
    }
}