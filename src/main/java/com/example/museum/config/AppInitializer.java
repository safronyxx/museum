package com.example.museum.config;

import com.example.museum.model.Role;
import com.example.museum.model.User;
import com.example.museum.model.Exhibition;
import com.example.museum.repository.UserRepository;
import com.example.museum.repository.ExhibitionRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Конфигурационный компонент, отвечающий за инициализацию базовых данных
 * при первом запуске приложения. Создаёт пользователей по умолчанию
 * и набор постоянных выставок, привязанных к гидам.
 * <p>
 * Инициализация выполняется один раз после полной загрузки контекста Spring.
 */
@Component
public class AppInitializer {

    private final UserRepository userRepository;
    private final ExhibitionRepository exhibitionRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * Конструктор для внедрения зависимостей через Spring.
     *
     * @param userRepository           репозиторий для работы с пользователями
     * @param exhibitionRepository     репозиторий для работы с выставками
     * @param passwordEncoder          компонент для хеширования паролей
     */
    public AppInitializer(UserRepository userRepository,
                          ExhibitionRepository exhibitionRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.exhibitionRepository = exhibitionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Инициализирует базовые данные: создаёт пользователей с разными ролями
     * и шесть постоянных выставок, каждая из которых привязана к одному из гидов.
     * <p>
     * Метод помечен аннотацией {@link PostConstruct}, поэтому вызывается
     * автоматически после создания бина Spring.
     */
    @PostConstruct
    public void init() {
        User guide1 = createDefaultUser("guide1@museum.com", "guidepass", Role.GUIDE, "Андрей Семёнович Крыжанов");
        User guide2 = createDefaultUser("guide2@museum.com", "guidepass", Role.GUIDE, "Ким Татьяна Ивановна");
        User guide3 = createDefaultUser("guide3@museum.com", "guidepass", Role.GUIDE, "Григорьев Семён Михайлович");
        User guide4 = createDefaultUser("guide4@museum.com", "guidepass", Role.GUIDE, "Иванов Игорь Максимович");
        User guide5 = createDefaultUser("guide5@museum.com", "guidepass", Role.GUIDE, "Самборский Олег Вадимович");
        User guide6 = createDefaultUser("guide6@museum.com", "guidepass", Role.GUIDE, "Краснова Екатерина Витальевна");


        createDefaultUser("superadmin@museum.com", "superpass", Role.SUPER_ADMIN, "СуперАдмин");
        createDefaultUser("admin@museum.com", "adminpass", Role.ADMIN, "Админ");
        createDefaultUser("visitor@museum.com", "visitorpass", Role.VISITOR, "Посетитель");


        createDefaultExhibition("Постоянная: Ботаника сквозь века",
                "Коллекция гербарных образцов XVIII–XX веков",
                guide1,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2100, 1, 1));

        createDefaultExhibition("Постоянная: Тропическая оранжерея",
                "Живые растения со всего мира",
                guide2,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2100, 1, 1));

        createDefaultExhibition("Постоянная: Эволюция зеленых",
                "От папоротников до цветковых",
                guide3,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2100, 1, 1));

        createDefaultExhibition("Постоянная: Лекарственные растения",
                "От фитотерапии до современной фармакологии",
                guide4,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2100, 1, 1));

        createDefaultExhibition("Постоянная: Деревья-долгожители",
                "История и экология вековых растений",
                guide5,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2100, 1, 1));

        createDefaultExhibition("Постоянная: Символика растений в культуре и искусстве",
                "Зелень бок о бок с культурой",
                guide6,
                LocalDate.of(2000, 1, 1),
                LocalDate.of(2100, 1, 1));
    }


    /**
     * Создаёт пользователя с заданными параметрами, если он ещё не существует в базе данных.
     *
     * @param email        email пользователя (используется как логин)
     * @param rawPassword  пароль в открытом виде (будет зашифрован)
     * @param role         роль пользователя
     * @param fullName     полное имя пользователя
     * @return созданный объект {@link User} или существующий из БД, если email уже занят
     */
    private User createDefaultUser(String email, String rawPassword, Role role, String fullName) {
        if (!userRepository.existsByEmail(email)) {
            User user = new User(email, passwordEncoder.encode(rawPassword), role, fullName);
            userRepository.save(user);
            return user;
        }
        // если пользователь уже существует то возвращаем его из бд
        return userRepository.findByEmail(email).orElse(null);
    }

    /**
     * Создаёт выставку с заданными параметрами, если выставка с таким названием ещё не существует.
     *
     * @param title       название выставки
     * @param description описание выставки
     * @param curator     объект пользователя-гида, отвечающего за выставку
     * @param startDate   дата начала выставки
     * @param endDate     дата окончания выставки
     */
    private void createDefaultExhibition(String title, String description, User curator,
                                         LocalDate startDate, LocalDate endDate) {
        if (exhibitionRepository.countByTitle(title) == 0) {
            Exhibition exhibition = new Exhibition();
            exhibition.setTitle(title);
            exhibition.setDescription(description);
            exhibition.setCurator(curator); // теперь curator — объект User
            exhibition.setStartDate(startDate);
            exhibition.setEndDate(endDate);
            exhibitionRepository.save(exhibition);
        }
    }
}