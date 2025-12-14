package com.example.museum.controller;

import com.example.museum.model.Exhibition;
import com.example.museum.model.Visit;
import com.example.museum.service.ExhibitionService;
import com.example.museum.service.UserService;
import com.example.museum.service.VisitService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/visits")
public class VisitController {

    private final VisitService visitService;
    private final ExhibitionService exhibitionService;
    private final UserService userService;

    public VisitController(VisitService visitService,
                           ExhibitionService exhibitionService,
                           UserService userService) {
        this.visitService = visitService;
        this.exhibitionService = exhibitionService;
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String showVisitsAndForm(
            @RequestParam(required = false) String sort,
            Model model,
            Authentication authentication,
            @RequestParam(required = false) String message) {

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            com.example.museum.model.User user = userService.findByEmail(email);
            model.addAttribute("currentUserFullName", user != null ? user.getFullName() : email);
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());

            boolean isVisitor = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_VISITOR"));

            if (isVisitor) {
                String sortDir = (sort != null && "desc".equals(sort)) ? "desc" : "asc";
                List<Visit> myVisits = visitService.findVisitsByVisitorEmail(email, sortDir);
                model.addAttribute("visits", myVisits);
                model.addAttribute("currentSort", sortDir);
                model.addAttribute("isVisitorView", true);
            } else {
                if (sort != null && ("asc".equals(sort) || "desc".equals(sort))) {
                    model.addAttribute("visits", visitService.findAllSortedByExhibitionTitle(sort));
                    model.addAttribute("currentSort", sort);
                } else {
                    model.addAttribute("visits", visitService.findAll());
                    model.addAttribute("currentSort", "asc");
                }
                model.addAttribute("isVisitorView", false);
            }

            if (message != null) {
                model.addAttribute("message", message);
            }

            model.addAttribute("currentUserEmail", email);
        }

        model.addAttribute("exhibitions", exhibitionService.findAll());
        model.addAttribute("visit", new Visit());

        return "visits";
    }

    @PostMapping("/add")
    public String addVisit(@ModelAttribute Visit visit,
                           Authentication authentication,
                           RedirectAttributes redirectAttributes) {
        if (authentication != null && authentication.isAuthenticated()) {
            visit.setVisitorEmail(authentication.getName());
            visitService.save(visit);
            redirectAttributes.addFlashAttribute("message", "Посещение успешно зарегистрировано");
        }
        return "redirect:/visits";
    }
}
