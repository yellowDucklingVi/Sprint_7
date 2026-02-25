package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.models.Courier;
import ru.yandex.practicum.models.CourierCredentials;
import ru.yandex.practicum.steps.CourierSteps;

import static org.hamcrest.Matchers.equalTo;

@DisplayName("Тесты на создание курьера")
public class CourierCreateTest {
    private CourierSteps steps;
    private int courierId;

    @Before
    public void setUp() {
        steps = new CourierSteps();
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            steps.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Курьера можно создать")
    public void testCreateCourierSuccess() {
        String uniqueLogin = "test_courier_" + System.currentTimeMillis();
        Courier courier = new Courier(uniqueLogin, "password123", "Иван");

        Response response = steps.createCourier(courier);
        response.then().statusCode(201).body("ok", equalTo(true));

        Response loginResponse = steps.loginCourier(new CourierCredentials(uniqueLogin, "password123"));
        courierId = loginResponse.path("id");
    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    public void testCreateDuplicateCourier() {
        String uniqueLogin = "test_courier_" + System.currentTimeMillis();
        Courier courier = new Courier(uniqueLogin, "password123", "Иван");

        steps.createCourier(courier).then().statusCode(201);
        Response loginResponse = steps.loginCourier(new CourierCredentials(uniqueLogin, "password123"));
        courierId = loginResponse.path("id");

        Response response2 = steps.createCourier(courier);
        response2.then().statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Создание курьера без пароля возвращает ошибку")
    public void testCreateCourierMissingPassword() {
        String login = "test_courier_" + System.currentTimeMillis();
        Courier noPassword = new Courier(login, null, "Иван");
        steps.createCourier(noPassword).then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Создание курьера без логина возвращает ошибку")
    public void testCreateCourierMissingLogin() {
        String login = "test_courier_" + System.currentTimeMillis();
        Courier noLogin = new Courier(null, "password123", "Иван");
        steps.createCourier(noLogin).then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Если создать пользователя с логином, который уже есть, возвращается ошибка")
    public void testCreateCourierDuplicateLoginError() {
        String login = "duplicate_login_" + System.currentTimeMillis();
        Courier first = new Courier(login, "pass1", "Name1");
        steps.createCourier(first).then().statusCode(201);

        Response loginResponse = steps.loginCourier(new CourierCredentials(login, "pass1"));
        courierId = loginResponse.path("id");

        Courier second = new Courier(login, "pass2", "Name2");
        Response response2 = steps.createCourier(second);
        response2.then().statusCode(409)
                .body("message", equalTo("Этот логин уже используется"));
    }
}