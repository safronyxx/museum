package com.example.museum.repository;

import com.example.museum.model.Hall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends JpaRepository<Hall, Long> {
    List<Hall> findByNameContainingIgnoreCase(String name);
    List<Hall> findByFloor(Integer floor);
    List<Hall> findByNameContainingIgnoreCaseAndFloor(String name, Integer floor);
}