package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.models.Courier;
import ru.yandex.practicum.models.CourierCredentials;
import ru.yandex.practicum.steps.CourierSteps;

import static org.hamcrest.Matchers.*;

@DisplayName("Тесты на логин курьера")
public class CourierLoginTest {
    private CourierSteps steps;
    private Courier courier;
    private int courierId;

    @Before
    public void setUp() {
        steps = new CourierSteps();
        String uniqueLogin = "login_test_" + System.currentTimeMillis();
        courier = new Courier(uniqueLogin, "password123", "Иван");
        steps.createCourier(courier).then().statusCode(201);
        Response loginResponse = steps.loginCourier(new CourierCredentials(uniqueLogin, "password123"));
        courierId = loginResponse.path("id");
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
            steps.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Курьер может авторизоваться")
    public void testLoginSuccess() {
        CourierCredentials creds = new CourierCredentials(courier.getLogin(), courier.getPassword());
        Response response = steps.loginCourier(creds);
        response.then().statusCode(200).body("id", notNullValue());
    }

    @Test
    @DisplayName("Авторизация без пароля возвращает ошибку")
    public void testLoginMissingPassword() {
        CourierCredentials noPass = new CourierCredentials(courier.getLogin(), null);
        steps.loginCourier(noPass).then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без логина возвращает ошибку")
    public void testLoginMissingLogin() {
        CourierCredentials noLogin = new CourierCredentials(null, courier.getPassword());
        steps.loginCourier(noLogin).then().statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем возвращает ошибку")
    public void testLoginWrongPassword() {
        CourierCredentials wrongPass = new CourierCredentials(courier.getLogin(), "wrong");
        steps.loginCourier(wrongPass).then().statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неверным логином возвращает ошибку")
    public void testLoginWrongLogin() {
        CourierCredentials wrongLogin = new CourierCredentials("wrong_login", courier.getPassword());
        steps.loginCourier(wrongLogin).then().statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация под несуществующим пользователем возвращает ошибку")
    public void testLoginNonExistentUser() {
        CourierCredentials nonExistent = new CourierCredentials("nonexistent_" + System.currentTimeMillis(), "pass");
        steps.loginCourier(nonExistent).then().statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Успешный запрос возвращает id")
    public void testLoginReturnsId() {
        CourierCredentials creds = new CourierCredentials(courier.getLogin(), courier.getPassword());
        steps.loginCourier(creds).then().body("id", notNullValue());
    }
}