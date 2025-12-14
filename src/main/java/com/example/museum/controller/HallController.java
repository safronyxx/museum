package com.example.museum.controller;

import com.example.museum.model.Hall;
import com.example.museum.service.HallService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/halls")
public class HallController {

    private final HallService hallService;
    private final com.example.museum.service.UserService userService;

    public HallController(HallService hallService,
                          com.example.museum.service.UserService userService) {
        this.hallService = hallService;
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String listHalls(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer floor,
            Model model,
            Authentication authentication) {

        List<Hall> halls;
        if (name != null || floor != null) {
            halls = hallService.searchByNameAndFloor(name, floor);
        } else {
            halls = hallService.findAll();
        }

        model.addAttribute("halls", halls);
        model.addAttribute("hall", new Hall());
        model.addAttribute("searchName", name);
        model.addAttribute("searchFloor", floor);

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            com.example.museum.model.User user = userService.findByEmail(email);
            model.addAttribute("currentUserFullName", user != null ? user.getFullName() : email);
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }

        return "halls";
    }


    @PostMapping("/add")
    public String addHall(@Valid @ModelAttribute("hall") Hall hall,
                          BindingResult bindingResult,
                          Model model,
                          Authentication authentication) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            model.addAttribute("hall", hall);
            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("currentUserEmail", authentication.getName());
                model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
            }
            return "halls";
        }
        hallService.save(hall);
        return "redirect:/halls";
    }

    @GetMapping("/edit/{id}")
    public String editHall(@PathVariable Long id, Model model, Authentication authentication) {
        Hall hall = hallService.findById(id);
        if (hall == null) {
            return "redirect:/halls";
        }

        model.addAttribute("halls", hallService.findAll());
        model.addAttribute("hall", hall);
        model.addAttribute("editingId", id);

        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("currentUserEmail", authentication.getName());
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }

        return "halls";
    }

    @PostMapping("/save")
    public String saveHall(@Valid @ModelAttribute("hall") Hall hall,
                           BindingResult bindingResult,
                           Model model,
                           Authentication authentication) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("halls", hallService.findAll());
            model.addAttribute("hall", hall);
            model.addAttribute("editingId", hall.getId());
            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("currentUserEmail", authentication.getName());
                model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
            }
            return "halls";
        }
        hallService.save(hall);
        return "redirect:/halls";
    }

    @GetMapping("/delete/{id}")
    public String deleteHall(@PathVariable Long id) {
        hallService.deleteById(id);
        return "redirect:/halls";
    }
}