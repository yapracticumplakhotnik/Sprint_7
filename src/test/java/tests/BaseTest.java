package tests;


import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.BeforeClass;

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
}