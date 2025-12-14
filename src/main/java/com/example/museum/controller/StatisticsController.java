package com.example.museum.controller;

import com.example.museum.model.Exhibition;
import com.example.museum.model.User;
import com.example.museum.repository.ExhibitionRepository;
import com.example.museum.repository.VisitRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class StatisticsController {

    private final ExhibitionRepository exhibitionRepository;
    private final VisitRepository visitRepository;
    private final com.example.museum.service.UserService userService;

    public StatisticsController(ExhibitionRepository exhibitionRepository,
                                VisitRepository visitRepository,
                                com.example.museum.service.UserService userService) {
        this.exhibitionRepository = exhibitionRepository;
        this.visitRepository = visitRepository;
        this.userService = userService;
    }

    @GetMapping("/statistics")
    public String showStatistics(Model model, Authentication authentication) {
        List<Exhibition> exhibitions = exhibitionRepository.findAll();
        List<Object[]> stats = exhibitions.stream()
                .map(exhibition -> new Object[]{
                        exhibition.getTitle(),
                        visitRepository.countByExhibitionId(exhibition.getId())
                })
                .collect(Collectors.toList());

        model.addAttribute("statistics", stats);

        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            com.example.museum.model.User user = userService.findByEmail(email);
            model.addAttribute("currentUserFullName", user != null ? user.getFullName() : email);
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }

        List<Object[]> guideStats = exhibitionRepository.countExhibitionsByCuratorEmail();
        List<String> guideNames = new ArrayList<>();
        List<Integer> guideCounts = new ArrayList<>();

        for (Object[] row : guideStats) {
            String email = (String) row[0];
            Long count = (Long) row[1];
            User guide = userService.findByEmail(email);
            guideNames.add(guide != null ? guide.getFullName() : email);
            guideCounts.add(count.intValue());
        }

        model.addAttribute("guideExhibitionLabels", guideNames);
        model.addAttribute("guideExhibitionData", guideCounts);


        return "statistics";
    }
}