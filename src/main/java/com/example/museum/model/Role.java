package com.example.museum.model;


/**
 * Перечисление ролей пользователей в системе.
 * <p>
 * Определяет четыре уровня доступа:
 * <ul>
 *     <li>{@code VISITOR} — посетитель (чтение каталога, регистрация на выставки)</li>
 *     <li>{@code GUIDE} — экскурсовод (просмотр своих выставок)</li>
 *     <li>{@code ADMIN} — администратор (управление контентом)</li>
 *     <li>{@code SUPER_ADMIN} — суперадминистратор (полный доступ)</li>
 * </ul>
 */
public enum Role {
    VISITOR,
    GUIDE,
    ADMIN,
    SUPER_ADMIN
}