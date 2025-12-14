package com.example.museum.service;

import com.example.museum.model.Exhibit;
import com.example.museum.repository.ExhibitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExhibitServiceImpl implements ExhibitService {

    private final ExhibitRepository exhibitRepository;

    public ExhibitServiceImpl(ExhibitRepository exhibitRepository) {
        this.exhibitRepository = exhibitRepository;
    }

    @Override
    public List<Exhibit> findAll() {
        return exhibitRepository.findAll();
    }

    @Override
    public Exhibit findById(Long id) {
        return exhibitRepository.findById(id).orElse(null);
    }

    @Override
    public Exhibit save(Exhibit exhibit) {
        return exhibitRepository.save(exhibit);
    }

    @Override
    public void deleteById(Long id) {
        exhibitRepository.deleteById(id);
    }

    @Override
    public List<Exhibit> searchByAuthorAndEra(String author, String era) {
        String cleanAuthor = (author != null) ? author.trim() : "";
        String cleanEra = (era != null) ? era.trim() : "";

        if (cleanAuthor.isEmpty() && cleanEra.isEmpty()) {
            return findAll();
        } else if (cleanAuthor.isEmpty()) {
            return exhibitRepository.findByEraContainingIgnoreCase(cleanEra);
        } else if (cleanEra.isEmpty()) {
            return exhibitRepository.findByAuthorContainingIgnoreCase(cleanAuthor);
        } else {
            return exhibitRepository.findByAuthorContainingIgnoreCaseAndEraContainingIgnoreCase(cleanAuthor, cleanEra);
        }
    }
}