package ru.yandex.practicum.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import ru.yandex.practicum.models.Courier;
import ru.yandex.practicum.models.CourierCredentials;

import static io.restassured.RestAssured.given;

public class CourierSteps {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru";

    @Step("Создание курьера")
    public Response createCourier(Courier courier) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(courier)
                .when()
                .post("/api/v1/courier");
    }

    @Step("Логин курьера")
    public Response loginCourier(CourierCredentials credentials) {
        return given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(credentials)
                .when()
                .post("/api/v1/courier/login");
    }

    @Step("Удаление курьера")
    public Response deleteCourier(int courierId) {
        return given()
                .baseUri(BASE_URL)
                .when()
                .delete("/api/v1/courier/" + courierId);
    }
}