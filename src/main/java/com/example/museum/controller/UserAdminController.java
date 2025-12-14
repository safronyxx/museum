package com.example.museum.controller;

import com.example.museum.model.Role;
import com.example.museum.model.User;
import com.example.museum.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserAdminController {

    private final UserRepository userRepository;
    private final com.example.museum.service.UserService userService;

    public UserAdminController(UserRepository userRepository,
                               com.example.museum.service.UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model, Authentication authentication) {
        model.addAttribute("users", userRepository.findAll());

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            com.example.museum.model.User user = userService.findByEmail(email);
            model.addAttribute("currentUserFullName", user != null ? user.getFullName() : email);
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }

        return "users";
    }

    @PostMapping("/{id}/role")
    public String updateRole(@PathVariable Long id, @RequestParam String role, Model model) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            if (user.getRole() != Role.SUPER_ADMIN) {
                user.setRole(Role.valueOf(role));
                userRepository.save(user);
            }
        }
        return "redirect:/users";
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && user.getRole() != Role.SUPER_ADMIN) {
            userRepository.deleteById(id);
        }
        return "redirect:/users";
    }
}