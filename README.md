[![Actions Status](https://github.com/saymon-says/java-project-lvl5/workflows/my-project-check/badge.svg)](https://github.com/saymon-says/java-project-lvl5/actions)
[![Maintainability](https://api.codeclimate.com/v1/badges/a86105c3641322266001/maintainability)](https://codeclimate.com/github/saymon-says/java-project-lvl5/maintainability)

### Описание

___
[Task Manager](https://to-do-my-list.herokuapp.com/) – система управления задачами, подобная http://www.redmine.org/.
Она позволяет ставить задачи, назначать исполнителей и менять их статусы. Для работы с системой требуется регистрация и
аутентификация.

### Реализовано

___

- регистрация и авторизация(JWT) на сайте
- валидация введенных данных
- хеширование пароля
- создание меток
- создание статусов
- создание задач
- возможность редактирования/удаления данных в зависимости от авторизованности
- фильтрация данных - имеет возможность фильтровать задачи по статусу, исполнителю, наличию метки, и имеет возможность
  отображать задачи, автором которых является текущий пользователь.
- обработка ошибок с выводом сообщений
- подключение frontend

### Технологии и подход к разработке

___

- REST + Open API (swagger)
- ORM
- Service layer
- БД H2 для разработки
- Liquibase
- Spring MVC
- Spring Security
- Spring Data JPA
- queryDSL
- Heroku + PostgreSQL

### Требования

___

* OpenJDK_17
* Gradle 7.3
* Make

### Запуск

___
Генерация миграций БД

```makefile
  make generate-migrations
  ```

Старт приложения

```makefile
  make start
```

Приложение будет запущено по адресу https://localhost:5000/

Запуск тестов

```makefile
  make test
```

### Open-Api

___
Интерактивная документация (Swagger) после запуска приложения доступна по https://localhost:5000/swagger-ui.html

### Тестовые данные

___
Логин - пароль

- <1@gmail.com> - 1234
- <2@gmail.com> - 1234

### Пример

___
![сайт](https://user-images.githubusercontent.com/43708964/153208590-68ca78b7-6860-4b53-b47b-1f46217032cb.jpg)

![сайт](https://user-images.githubusercontent.com/43708964/153208867-6a228fa4-a526-414e-b205-c546451d4118.jpg)
