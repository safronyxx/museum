package com.example.museum.repository;

import com.example.museum.model.Exhibition;
import com.example.museum.model.Role;
import com.example.museum.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExhibitionRepository extends JpaRepository<Exhibition, Long> {
    long countByTitle(String title);
    @Query("SELECT e FROM Exhibition e WHERE e.curator.email = :email")
    List<Exhibition> findByCuratorEmail(@Param("email") String email);
    @Query("SELECT e.curator.email, COUNT(e) FROM Exhibition e WHERE e.curator IS NOT NULL GROUP BY e.curator.email")
    List<Object[]> countExhibitionsByCuratorEmail();
}