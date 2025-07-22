package tests;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.*;
import static org.hamcrest.Matchers.*;
import static utils.Constants.*;

public class CourierCreationTest extends BaseTest {

    CreateCourier createCourierBody = new CreateCourier("testCourierLoginJenya","testPassword","testName");

    @Test
    @DisplayName("Метод POST to /api/v1/courier - запрос возвращает правильный код ответа и успешный запрос возвращает ok: true")
    public void testCreateCourierSuccess() {
        givenRequest()
                .body(createCourierBody)
                .when()
                .post(createCourier)
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));


    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier - нельзя создать двух одинаковых курьеров")
    public void testCreateDuplicateCourier() {
        givenRequest()
                .body(createCourierBody)
                .when()
                .post(createCourier)
                .then()
                .statusCode(409); // ожидается конфликт
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier - если одного из полей нет, запрос возвращает ошибку")
    public void testCreateCourierMissingFields() {

        String body = "{ \"login\": \"" + System.currentTimeMillis() + "\" }";

        givenRequest()
                .body(body)
                .when()
                .post(createCourier)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("Метод DELETE to /api/v1/courier/{id} - удалить курьера")
    public void deleteCourier() {

        //авторизация для получения id
        Response loginResponse = givenRequest()
                .body(createCourierBody)
                .when()
                .post(loginCourier);
        loginResponse.then().statusCode(200);
        int courierId = loginResponse.jsonPath().getInt("id");
        //удаление курьера по id
        Response deleteResponse = givenRequest()
                .when()
                .delete(createCourier + courierId);
        //проверка статуса и отсутствия курьера
        deleteResponse.then()
                .statusCode(anyOf(is(200), is(204)));
        //проверка, что курьер больше не существует
        givenRequest()
                .body(createCourierBody)
                .when()
                .post(loginCourier)
                .then()
                .statusCode(404);
        }
}
