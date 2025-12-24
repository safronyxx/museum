package com.example.museum.controller;

import com.example.museum.model.Role;
import com.example.museum.model.User;
import com.example.museum.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


/**
 * Контроллер для управления пользователями системы.
 * <p>
 * Позволяет просматривать список всех пользователей, изменять их роли
 * и удалять учётные записи. Доступ к функционалу разрешён исключительно
 * суперадминистратору ({@code SUPER_ADMIN}).
 */
@Controller
@RequestMapping("/users")
public class UserAdminController {

    private final UserRepository userRepository;
    private final com.example.museum.service.UserService userService;


    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param userRepository репозиторий для работы с пользователями
     * @param userService    сервис для работы с пользователями
     */
    public UserAdminController(UserRepository userRepository,
                               com.example.museum.service.UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }


    /**
     * Отображает список всех зарегистрированных пользователей.
     *
     * @param model          объект модели
     * @param authentication объект аутентификации
     * @return имя шаблона "users"
     */
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


    /**
     * Обновляет роль указанного пользователя.
     * <p>
     * Изменение роли {@code SUPER_ADMIN} запрещено в целях безопасности.
     *
     * @param id      идентификатор пользователя
     * @param role    новая роль (в виде строки)
     * @param model   объект модели
     * @return перенаправление на список пользователей
     */
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


    /**
     * Удаляет учётную запись пользователя.
     * <p>
     * Удаление пользователя с ролью {@code SUPER_ADMIN} запрещено.
     *
     * @param id идентификатор удаляемого пользователя
     * @return перенаправление на список пользователей
     */
    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null && user.getRole() != Role.SUPER_ADMIN) {
            userRepository.deleteById(id);
        }
        return "redirect:/users";
    }
}