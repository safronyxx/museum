package com.example.museum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Главный класс информационно-справочной системы музея.
 * <p>
 * Содержит точку входа в приложение и запускает Spring Boot-контекст.
 * Аннотация {@link SpringBootApplication} автоматически включает следующие функции:
 * <ul>
 *   <li>Конфигурацию на основе аннотаций ({@code @Configuration})</li>
 *   <li>Сканирование компонентов в текущем пакете и подпакетах ({@code @ComponentScan})</li>
 *   <li>Автонастройку Spring Boot ({@code @EnableAutoConfiguration})</li>
 * </ul>
 */
@SpringBootApplication
public class MuseumApplication {

	/**
	 * Точка входа в приложение.
	 * <p>
	 * Инициализирует контекст Spring Boot, запускает встроенный веб-сервер (Tomcat)
	 * и делает приложение доступным по указанному порту (по умолчанию 8080).
	 *
	 * @param args аргументы командной строки, передаваемые при запуске
	 */
	public static void main(String[] args) {
		SpringApplication.run(MuseumApplication.class, args);
	}
}
