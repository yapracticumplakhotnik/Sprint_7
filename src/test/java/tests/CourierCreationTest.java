package tests;

import client.Courier;
import client.DeleteCourier;
import client.QAScooterAPIClient;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.*;
import static org.hamcrest.Matchers.*;


public class CourierCreationTest  {

    @Test
    @DisplayName("Метод POST to /api/v1/courier - запрос возвращает правильный код ответа и успешный запрос возвращает ok: true")
    public void testCreateCourierSuccess() {
        QAScooterAPIClient client = new QAScooterAPIClient();
        Courier courier = new Courier("testCourierLoginJenya","testPassword","testName");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(201).and().body("ok",equalTo(true));
        DeleteCourier.deleteCourier();
    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier - нельзя создать двух одинаковых курьеров - получим 409 код")
    public void testCreateDuplicateCourier() {
        //первый курьер создаем успешно
        QAScooterAPIClient client = new QAScooterAPIClient();
        Courier courier = new Courier("testCourierLoginJenya","testPassword","testName");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(201);
        //второй курьер копия
        QAScooterAPIClient client2 = new QAScooterAPIClient();
        Courier courier2 = new Courier("testCourierLoginJenya","testPassword","testName");
        ValidatableResponse response2 = client2.createCourier(courier2);
        response2.assertThat().statusCode(409);
        DeleteCourier.deleteCourier();

    }

    @Test
    @DisplayName("Метод POST to /api/v1/courier - если одного из полей нет, запрос возвращает ошибку")
    public void testCreateCourierMissingFields() {
        QAScooterAPIClient client = new QAScooterAPIClient();
        Courier courier = new Courier("testCourierLoginJenya","", "");
        ValidatableResponse response = client.createCourier(courier);
        response.assertThat().statusCode(400);
    }
}
