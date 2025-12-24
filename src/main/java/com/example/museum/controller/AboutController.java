package com.example.museum.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Контроллер для отображения страницы "Об авторе".
 * <p>
 * Обеспечивает передачу информации о текущем пользователе (если он авторизован)
 * в шаблон Thymeleaf для персонализированного отображения.
 */
@Controller
public class AboutController {

    private final com.example.museum.service.UserService userService;

    /**
     * Конструктор для внедрения зависимости сервиса пользователей.
     *
     * @param userService сервис для работы с пользователями
     */
    public AboutController(com.example.museum.service.UserService userService) {
        this.userService = userService;
    }


    /**
     * Обрабатывает GET-запрос к странице "/about".
     * <p>
     * Если пользователь авторизован, в модель добавляются его полное имя
     * и список ролей для отображения в навбаре.
     *
     * @param model         объект модели для передачи данных в представление
     * @param authentication объект аутентификации Spring Security
     * @return имя шаблона Thymeleaf "about"
     */
    @GetMapping("/about")
    public String showAboutPage(Model model, Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            String email = authentication.getName();
            com.example.museum.model.User user = userService.findByEmail(email);
            model.addAttribute("currentUserFullName", user != null ? user.getFullName() : email);
            model.addAttribute("currentUserAuthorities", authentication.getAuthorities());
        }
        return "about";
    }
}