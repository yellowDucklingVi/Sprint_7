package ru.yandex.practicum.tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import ru.yandex.practicum.steps.OrderSteps;

import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты на получение списка заказов")
public class OrderListTest {
    private OrderSteps steps;

    @Before
    public void setUp() {
        steps = new OrderSteps();
    }

    @Test
    @DisplayName("Получение списка заказов")
    public void testGetOrdersList() {
        Response response = steps.getOrders();
        response.then().statusCode(200).body("orders", notNullValue());
    }
}