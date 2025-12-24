package com.example.museum.controller;

import com.example.museum.model.User;
import com.example.museum.model.Role;
import com.example.museum.repository.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Контроллер для обработки аутентификации: входа и регистрации пользователей.
 * <p>
 * Отвечает за отображение форм входа и регистрации, а также за создание
 * нового пользователя с ролью по умолчанию "VISITOR".
 */
@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор для внедрения зависимостей.
     *
     * @param userRepository репозиторий для работы с пользователями
     * @param passwordEncoder компонент для хеширования паролей
     */
    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Отображает форму входа в систему.
     *
     * @return путь к шаблону "auth/login"
     */
    @GetMapping("/login")
    public String showLoginForm() {
        return "auth/login";
    }

    /**
     * Отображает форму регистрации нового пользователя.
     *
     * @param model объект модели
     * @return путь к шаблону "auth/register"
     */
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/register";
    }


    /**
     * Обрабатывает POST-запрос на регистрацию нового пользователя.
     * <p>
     * Проверяет валидность данных, уникальность email и, в случае успеха,
     * сохраняет пользователя в БД с ролью {@link Role#VISITOR} и зашифрованным паролем.
     *
     * @param user           объект пользователя из формы
     * @param bindingResult  результат валидации
     * @param model          объект модели
     * @return имя шаблона "auth/register" в случае ошибки или перенаправление на ту же страницу с сообщением об успехе
     */
    @PostMapping("/register")
    public String doRegister(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/register";
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            model.addAttribute("message", "Пользователь с таким email уже существует.");
            return "auth/register";
        }

        // роль по умолчанию посетитель
        user.setRole(Role.VISITOR);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

        model.addAttribute("message", "Регистрация прошла успешно");
        return "auth/register";
    }
}