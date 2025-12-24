package com.example.museum.service;

import com.example.museum.model.User;
import com.example.museum.model.Role;
import com.example.museum.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Реализация интерфейса {@link UserDetailsService} для аутентификации
 * пользователей на основе данных из репозитория {@link UserRepository}.
 * <p>
 * Преобразует сущность {@link User} в объект Spring Security {@link UserDetails},
 * необходимый для управления сессией и авторизацией.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Конструктор для внедрения зависимости репозитория пользователей.
     *
     * @param userRepository репозиторий для доступа к данным пользователей
     */
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    /**
     * Загружает данные пользователя по email-адресу (используется как логин).
     * <p>
     * Если пользователь не найден, выбрасывается исключение
     * {@link UsernameNotFoundException}.
     *
     * @param email email-адрес пользователя
     * @return объект {@link UserDetails}, содержащий данные для аутентификации
     * @throws UsernameNotFoundException если пользователь с указанным email не существует
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        String roleName = user.getRole().name();
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleName));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                authorities
        );
    }
}