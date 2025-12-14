package com.example.museum.controller;

import com.example.museum.model.Exhibit;
import com.example.museum.model.Hall;
import com.example.museum.service.ExhibitService;
import com.example.museum.service.HallService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/exhibits")
public class ExhibitController {

    private final ExhibitService exhibitService;
    private final HallService hallService;
    private final com.example.museum.service.UserService userService;

    public ExhibitController(ExhibitService exhibitService, HallService hallService,
                             com.example.museum.service.UserService userService) {
        this.exhibitService = exhibitService;
        this.hallService = hallService;
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String listExhibits(
            @RequestParam(required = false) String author,
            @RequestParam(required = false) String era,
            Model model,
            Authentication authentication) {

        List<Exhibit> exhibits;
        if (author != null || era != null) {
            exhibits = exhibitService.searchByAuthorAndEra(author, era);
        } else {
            exhibits = exhibitService.findAll();
        }

        model.addAttribute("exhibits", exhibits);
        model.addAttribute("exhibit", new Exhibit());
        model.addAttribute("halls", hallService.findAll());
        model.addAttribute("searchAuthor", author);
        model.addAttribute("searchEra", era);

        // ← Обновление: передаём полное имя
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            com.example.museum.model.User user = userService.findByEmail(email);
            model.addAttribute("currentUserFullName", user != null ? user.getFullName() : email);
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }

        return "exhibits";
    }

    @PostMapping("/add")
    public String addExhibit(@Valid @ModelAttribute("exhibit") Exhibit exhibit,
                             BindingResult bindingResult,
                             Model model,
                             Authentication authentication) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("exhibits", exhibitService.findAll());
            model.addAttribute("halls", hallService.findAll());
            model.addAttribute("exhibit", exhibit);
            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("currentUserEmail", authentication.getName());
                model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
            }
            return "exhibits";
        }
        exhibitService.save(exhibit);
        return "redirect:/exhibits";
    }

    @GetMapping("/edit/{id}")
    public String editExhibit(@PathVariable Long id, Model model, Authentication authentication) {
        Exhibit exhibit = exhibitService.findById(id);
        if (exhibit == null) {
            return "redirect:/exhibits";
        }

        model.addAttribute("exhibits", exhibitService.findAll());
        model.addAttribute("exhibit", exhibit);
        model.addAttribute("editingId", id);
        model.addAttribute("halls", hallService.findAll());

        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("currentUserEmail", authentication.getName());
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }

        return "exhibits";
    }

    @PostMapping("/save")
    public String saveExhibit(@Valid @ModelAttribute("exhibit") Exhibit exhibit,
                              BindingResult bindingResult,
                              Model model,
                              Authentication authentication) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("exhibits", exhibitService.findAll());
            model.addAttribute("halls", hallService.findAll());
            model.addAttribute("exhibit", exhibit);
            model.addAttribute("editingId", exhibit.getId());
            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("currentUserEmail", authentication.getName());
                model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
            }
            return "exhibits";
        }
        exhibitService.save(exhibit);
        return "redirect:/exhibits";
    }

    @GetMapping("/delete/{id}")
    public String deleteExhibit(@PathVariable Long id) {
        exhibitService.deleteById(id);
        return "redirect:/exhibits";
    }
}