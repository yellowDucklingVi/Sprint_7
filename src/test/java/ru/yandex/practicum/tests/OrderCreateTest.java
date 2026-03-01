package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.practicum.models.Order;
import ru.yandex.practicum.steps.OrderSteps;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
@DisplayName("Тесты на создание заказа")
public class OrderCreateTest {
    private OrderSteps steps;
    private final List<String> color;

    public OrderCreateTest(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвета: {0}")
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Arrays.asList("BLACK")},
                {Arrays.asList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {Arrays.asList()}
        });
    }

    @Before
    public void setUp() {
        steps = new OrderSteps();
    }

    @Test
    @DisplayName("Создание заказа с различными цветами")
    public void testCreateOrderWithColors() {
        Order order = new Order(
                "Иван",
                "Иванов",
                "Москва, ул. Ленина, 1",
                "5",
                "+79991234567",
                3,
                "2025-03-01",
                "Комментарий",
                color
        );

        Response response = steps.createOrder(order);
        response.then().statusCode(201)
                .body("track", notNullValue())
                .body("track", instanceOf(Integer.class))
                .body("track", greaterThan(0));
    }
}