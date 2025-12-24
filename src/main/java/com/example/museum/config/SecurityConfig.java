package com.example.museum.config;

import com.example.museum.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Конфигурационный класс безопасности приложения на основе Spring Security.
 * <p>
 * Определяет правила доступа к URL-адресам, настраивает форму входа/выхода,
 * а также указывает механизм аутентификации и хеширования паролей.
 */
@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    /**
     * Конструктор для внедрения сервиса загрузки пользователей.
     *
     * @param userDetailsService сервис, реализующий {@link org.springframework.security.core.userdetails.UserDetailsService}
     */
    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


    /**
     * Настраивает цепочку фильтров безопасности Spring Security.
     * <p>
     * Определяет:
     * <ul>
     *   <li>какие ресурсы доступны без аутентификации (логин, регистрация, статика);</li>
     *   <li>какие URL-адреса требуют авторизации и какие роли имеют к ним доступ;</li>
     *   <li>поведение при входе и выходе из системы.</li>
     * </ul>
     *
     * @param http объект для конфигурации безопасности
     * @return настроенный {@link SecurityFilterChain}
     * @throws Exception если возникает ошибка при конфигурации
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/register", "/css/**", "/js/**", "/about").permitAll()
                        .requestMatchers("/halls/**", "/exhibits/**", "/exhibitions/**", "/visits/**", "/about/**")
                        .hasAnyRole("VISITOR", "GUIDE", "ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/users/**").hasRole("SUPER_ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/statistics").hasAnyRole("GUIDE", "ADMIN", "SUPER_ADMIN")
                        .requestMatchers("/my-exhibitions").hasAnyRole("GUIDE")
                        .anyRequest().authenticated()
                )

                // форма входа
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/exhibits", true)
                        .permitAll()
                )

                // выход
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true) // уничтожение сессии
                        .clearAuthentication(true) // сброс аутентификации
                        .permitAll()
                );


        return http.build();
    }

    /**
     * Создаёт и возвращает компонент для хеширования паролей с использованием алгоритма BCrypt.
     * <p>
     * Этот компонент используется при регистрации и аутентификации пользователей.
     *
     * @return экземпляр {@link PasswordEncoder}, основанный на BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}