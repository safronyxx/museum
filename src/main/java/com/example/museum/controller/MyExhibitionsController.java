package com.example.museum.controller;

import com.example.museum.model.Exhibition;
import com.example.museum.model.User;
import com.example.museum.service.ExhibitionService;
import com.example.museum.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * Контроллер для отображения страницы "Мои выставки", доступной гидам.
 * <p>
 * Позволяет гиду просматривать список выставок, куратором которых он является.
 * Доступ к странице ограничен ролью GUIDE.
 */
@Controller
public class MyExhibitionsController {

    private final ExhibitionService exhibitionService;
    private final UserService userService;


    /**
     * Конструктор для внедрения зависимостей через Spring.
     *
     * @param exhibitionService сервис для работы с выставками
     * @param userService       сервис для работы с пользователями
     */
    public MyExhibitionsController(ExhibitionService exhibitionService,
                                   UserService userService) {
        this.exhibitionService = exhibitionService;
        this.userService = userService;
    }


    /**
     * Отображает список выставок, куратором которых является текущий пользователь (гид).
     *
     * @param model          объект модели для передачи данных в представление
     * @param authentication объект аутентификации Spring Security
     * @return имя шаблона Thymeleaf "my-exhibitions"
     */
    @GetMapping("/my-exhibitions")
    public String myExhibitions(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            List<Exhibition> myExhibitions = exhibitionService.findByCuratorEmail(email);
            model.addAttribute("myExhibitions", myExhibitions);

            User user = userService.findByEmail(email);
            model.addAttribute("currentUserFullName", user != null ? user.getFullName() : email);
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }
        return "my-exhibitions";
    }
}