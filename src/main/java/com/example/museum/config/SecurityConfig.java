package com.example.museum.config;

import com.example.museum.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}