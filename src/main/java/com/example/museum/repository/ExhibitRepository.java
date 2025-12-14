package com.example.museum.repository;

import com.example.museum.model.Exhibit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExhibitRepository extends JpaRepository<Exhibit, Long> {

    List<Exhibit> findByAuthorContainingIgnoreCase(String author);
    List<Exhibit> findByEraContainingIgnoreCase(String era);
    List<Exhibit> findByAuthorContainingIgnoreCaseAndEraContainingIgnoreCase(String author, String era);

}