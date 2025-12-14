package com.example.museum.controller;

import com.example.museum.model.Exhibition;
import com.example.museum.model.User;
import com.example.museum.service.ExhibitionService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


@Controller
@RequestMapping("/exhibitions")
public class ExhibitionController {

    private final ExhibitionService exhibitionService;
    private final com.example.museum.service.UserService userService;

    public ExhibitionController(ExhibitionService exhibitionService,
                                com.example.museum.service.UserService userService) {
        this.exhibitionService = exhibitionService;
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String listExhibitions(Model model, Authentication authentication) {
        model.addAttribute("exhibitions", exhibitionService.findAll());
        model.addAttribute("exhibition", new Exhibition());

        List<User> guides = userService.findAllGuides();
        model.addAttribute("guides", guides);

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            com.example.museum.model.User user = userService.findByEmail(email);
            model.addAttribute("currentUserFullName", user != null ? user.getFullName() : email);
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }

        return "exhibitions";
    }

    @PostMapping("/add")
    public String addExhibition(@RequestParam String title,
                                @RequestParam String curatorEmail,
                                @RequestParam LocalDate startDate,
                                @RequestParam LocalDate endDate,
                                @RequestParam(required = false) String description,
                                Model model,
                                Authentication authentication) {

        if (endDate.isBefore(startDate)) {
            model.addAttribute("exhibitions", exhibitionService.findAll());
            model.addAttribute("exhibition", new Exhibition());
            model.addAttribute("guides", userService.findAllGuides());

            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("currentUserEmail", authentication.getName());
                model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
            }
            model.addAttribute("error", "Дата окончания не может быть раньше даты начала.");
            return "exhibitions";
        }

        User curator = userService.findByEmail(curatorEmail);
        if (curator == null) {
            model.addAttribute("exhibitions", exhibitionService.findAll());
            model.addAttribute("exhibition", new Exhibition());
            model.addAttribute("guides", userService.findAllGuides());
            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("currentUserEmail", authentication.getName());
                model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
            }
            model.addAttribute("error", "Выбранный куратор не найден.");
            return "exhibitions";
        }

        Exhibition exhibition = new Exhibition();
        exhibition.setTitle(title);
        exhibition.setCurator(curator);
        exhibition.setStartDate(startDate);
        exhibition.setEndDate(endDate);
        exhibition.setDescription(description);

        exhibitionService.save(exhibition);
        return "redirect:/exhibitions";
    }

    @GetMapping("/edit/{id}")
    public String editExhibition(@PathVariable Long id, Model model, Authentication authentication) {
        Exhibition exhibition = exhibitionService.findById(id);
        if (exhibition == null) {
            return "redirect:/exhibitions";
        }

        model.addAttribute("exhibition", exhibition);
        model.addAttribute("editingId", id);
        model.addAttribute("exhibitions", exhibitionService.findAll());

        List<User> guides = userService.findAllGuides();
        model.addAttribute("guides", guides);

        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("currentUserEmail", authentication.getName());
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }

        return "exhibitions";
    }


    @PostMapping("/save")
    public String saveExhibition(@RequestParam Long id,
                                 @RequestParam String title,
                                 @RequestParam String curatorEmail,
                                 @RequestParam LocalDate startDate,
                                 @RequestParam LocalDate endDate,
                                 @RequestParam(required = false) String description,
                                 Model model,
                                 Authentication authentication) {

        if (endDate.isBefore(startDate)) {
            Exhibition existing = exhibitionService.findById(id);
            model.addAttribute("exhibition", existing);
            model.addAttribute("editingId", id);
            model.addAttribute("exhibitions", exhibitionService.findAll());
            model.addAttribute("guides", userService.findAllGuides());

            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("currentUserEmail", authentication.getName());
                model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
            }
            model.addAttribute("error", "Дата окончания не может быть раньше даты начала.");
            return "exhibitions";
        }

        User curator = userService.findByEmail(curatorEmail);
        if (curator == null) {
            Exhibition existing = exhibitionService.findById(id);
            model.addAttribute("exhibition", existing);
            model.addAttribute("editingId", id);
            model.addAttribute("exhibitions", exhibitionService.findAll());
            model.addAttribute("guides", userService.findAllGuides());
            if (authentication != null && authentication.isAuthenticated()) {
                model.addAttribute("currentUserEmail", authentication.getName());
                model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
            }
            model.addAttribute("error", "Выбранный куратор не найден.");
            return "exhibitions";
        }

        Exhibition exhibition = new Exhibition();
        exhibition.setId(id);
        exhibition.setTitle(title);
        exhibition.setCurator(curator);
        exhibition.setStartDate(startDate);
        exhibition.setEndDate(endDate);
        exhibition.setDescription(description);

        exhibitionService.save(exhibition);
        return "redirect:/exhibitions";
    }

    @GetMapping("/delete/{id}")
    public String deleteExhibition(@PathVariable Long id) {
        exhibitionService.deleteById(id);
        return "redirect:/exhibitions";
    }
}