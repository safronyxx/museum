package com.example.museum.service;

import com.example.museum.model.User;
import java.util.List;

public interface UserService {
    List<User> findAll();
    User findById(Long id);
    User save(User user);
    void deleteById(Long id);
    boolean existsByEmail(String email);
    User findByEmail(String email);
    List<User> findAllGuides();
}