package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.yandex.practicum.models.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание заказа")
    public Response createOrder(Order order) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(order)
                .when()
                .post("/api/v1/orders");
    }

    @Step("Получение списка заказов")
    public Response getOrders() {
        return given()
                .baseUri(BASE_URL)
                .when()
                .get("/api/v1/orders");
    }
}