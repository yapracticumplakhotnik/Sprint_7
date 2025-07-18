package tests;


import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

import static utils.Constants.createCourier;

public class BaseTest {
    protected static String baseUrl = "https://qa-scooter.praktikum-services.ru";

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = baseUrl;
    }

    protected RequestSpecification givenRequest() {
        return io.restassured.RestAssured.given()
                .header("Content-Type", "application/json");
    }

    public int courierId;

    @DisplayName("Удаление курьера")
    public Response deleteCourier(int courierId) {
        return givenRequest()
                .header("Content-type", "application/json")
                .when()
                .delete(createCourier + "/" + courierId);
    }
    public void tearDown() {
        if (courierId != 0) {
            deleteCourier(courierId);
        }
    }
}