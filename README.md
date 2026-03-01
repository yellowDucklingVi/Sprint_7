# Автотесты для учебного сервиса Яндекс Самокат

Проект содержит автоматизированные тесты API для учебного сервиса Яндекс Самокат.

## Технологии

| Технологии       | Версия   |
|------------------|----------|
| Java             | 11       |
| JUnit            | 4.13.2   |
| RestAssured      | 4.4.0    |
| Allure           | 2.15.0   |
| Maven            | 3.9.11   |
| Jackson          | 2.13.0   |

## Структура проекта
- `src/test/java/ru/yandex/practicum/models/` - POJO классы (модели данных)
- `src/test/java/ru/yandex/practicum/steps/` - Классы с шагами API
    - `CourierSteps` - шаги для работы с курьером
    - `OrderSteps` - шаги для работы с заказами
- `src/test/java/ru/yandex/practicum/tests/` - Тестовые классы
- `pom.xml` - Конфигурация Maven

## Тесты
1. `CourierCreateTest` - тестирование создания курьера
2. `CourierLoginTest` - тестирование логина курьера
3. `OrderCreateTest` - тестирование создания заказа (параметризованный)
4. `OrderListTest` - тестирование получения списка заказов

## Запуск всех тестов и генерация отчета Allure
```bash
mvn clean test
mvn allure:serve