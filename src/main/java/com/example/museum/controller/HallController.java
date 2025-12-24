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

/**
 * Контроллер для управления информацией о залах музея.
 * <p>
 * Обеспечивает операции просмотра, поиска, добавления, редактирования и удаления залов.
 * Доступ к функциям управления (добавление, редактирование, удаление) ограничен ролями
 * {@code ADMIN} и {@code SUPER_ADMIN}.
 */
@Controller
@RequestMapping("/halls")
public class HallController {

    private final HallService hallService;
    private final com.example.museum.service.UserService userService;


    /**
     * Конструктор для внедрения зависимостей через Spring.
     *
     * @param hallService сервис для работы с залами
     * @param userService сервис для работы с пользователями (используется для получения данных текущего пользователя)
     */
    public HallController(HallService hallService,
                          com.example.museum.service.UserService userService) {
        this.hallService = hallService;
        this.userService = userService;
    }


    /**
     * Отображает список всех залов с возможностью поиска по названию и этажу.
     *
     * @param name          фильтр по названию зала (опционально)
     * @param floor         фильтр по этажу (опционально)
     * @param model         объект модели для передачи данных в представление
     * @param authentication объект аутентификации Spring Security
     * @return имя шаблона Thymeleaf "halls"
     */
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

    /**
     * Добавляет новый зал в систему.
     *
     * @param hall           объект зала, полученный из формы
     * @param bindingResult  результат валидации входных данных
     * @param model          объект модели
     * @param authentication объект аутентификации
     * @return имя шаблона "halls" в случае ошибки валидации, иначе перенаправление на список залов
     */
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


    /**
     * Отображает форму редактирования существующего зала.
     *
     * @param id             идентификатор зала
     * @param model          объект модели
     * @param authentication объект аутентификации
     * @return имя шаблона "halls"
     */
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

    /**
     * Сохраняет обновлённые данные зала.
     *
     * @param hall           обновлённый объект зала
     * @param bindingResult  результат валидации
     * @param model          объект модели
     * @param authentication объект аутентификации
     * @return имя шаблона "halls" в случае ошибки, иначе перенаправление
     */
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

    /**
     * Удаляет зал по его идентификатору.
     * <p>
     * Удаление возможно только для пользователей с соответствующими правами.
     *
     * @param id идентификатор удаляемого зала
     * @return перенаправление на страницу списка залов
     */
    @GetMapping("/delete/{id}")
    public String deleteHall(@PathVariable Long id) {
        hallService.deleteById(id);
        return "redirect:/halls";
    }
}