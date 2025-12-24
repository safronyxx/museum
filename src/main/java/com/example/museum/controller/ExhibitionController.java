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

/**
 * Контроллер для управления выставками.
 * <p>
 * Обеспечивает CRUD-операции и выбор куратора из списка гидов.
 */
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


    /**
     * Отображает список выставок и форму добавления.
     *
     * @param model          объект модели
     * @param authentication объект аутентификации
     * @return имя шаблона "exhibitions"
     */
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


    /**
     * Добавляет новую выставку.
     *
     * @param title          название выставки
     * @param curatorEmail   email куратора (гида)
     * @param startDate      дата начала
     * @param endDate        дата окончания
     * @param description    описание (опционально)
     * @param model          объект модели
     * @param authentication объект аутентификации
     * @return шаблон "exhibitions" или перенаправление
     */
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


    /**
     * Отображает форму редактирования выставки.
     *
     * @param id             идентификатор выставки
     * @param model          объект модели
     * @param authentication объект аутентификации
     * @return имя шаблона "exhibitions"
     */
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


    /**
     * Сохраняет обновлённую выставку.
     *
     * @param id             идентификатор выставки
     * @param title          название
     * @param curatorEmail   email куратора
     * @param startDate      дата начала
     * @param endDate        дата окончания
     * @param description    описание
     * @param model          объект модели
     * @param authentication объект аутентификации
     * @return перенаправление или шаблон с ошибкой
     */
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

    /**
     * Удаляет выставку по идентификатору.
     *
     * @param id идентификатор удаляемой выставки
     * @return перенаправление на список выставок
     */
    @GetMapping("/delete/{id}")
    public String deleteExhibition(@PathVariable Long id) {
        exhibitionService.deleteById(id);
        return "redirect:/exhibitions";
    }
}