package com.github.smartoven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AccountApplication {

    // TODO
    //  1) Решить, что нам нужно, а что не нужно искать в БД. Точно нужно искать User'ов.
    //     Скорее всего, стоит хранить существующие локации и их IP адреса в пропертях
    //  2) Дать пользователям доступ ко всем серверам. То есть, убрать сущность Access целиком.
    //     При запросе на сертификат, просто определять, на какой сервер его направлять, и уже там
    //     этот сертификат для пользователя создавать и хранить.
    //  3) Решить, какой класс будет заниматься менеджментом подписок

    // TODO Контроллеры и валидации
    //  1) Написать контроллеры для всего
    //  2) Написать валидации для всех DTO

    // TODO Рефакторинг
    //  1) Создать интерфейс маппера и реализовать его во всех мапперах
    //  2) Создать интерфейс/абстрактный класс сервиса и реализовать его во всех сервисах
    //  10) Написать тесты
    public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
    }

}
