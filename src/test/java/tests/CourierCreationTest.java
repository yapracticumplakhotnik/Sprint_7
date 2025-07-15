package tests;


import io.qameta.allure.Step;
import io.restassured.response.Response;
import org.junit.*;


import static org.hamcrest.Matchers.*;

public class CourierCreationTest extends BaseTest {

    private String courierLogin = "testCourierLogin";
    private String courierPassword = "testPassword";
    private String courierName = "testName";

    @Before
    @Step("Метод POST to /api/v1/courier - курьера можно создать")
    public void createCourier() {
        // Создаем курьера перед каждым тестом
        String body = "{ \"login\": \"" + courierLogin + "\", \"password\": \"" + courierPassword + "\", \"firstName\": \"" + courierName + "\" }";

        givenRequest()
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(anyOf(is(201), is(409))); // Если уже создан, 409
    }

    @Test
    @Step("Метод POST to /api/v1/courier - запрос возвращает правильный код ответа и успешный запрос возвращает ok: true")
    public void testCreateCourierSuccess() {
        String newLogin = "newCourier" + System.currentTimeMillis();
        String body = "{ \"login\": \"" + newLogin + "\", \"password\": \"pass123\", \"firstName\": \"Name\" }";
        givenRequest()
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201)
                .body("ok", equalTo(true));


    }

    @Test
    @Step("Метод POST to /api/v1/courier - нельзя создать двух одинаковых курьеров")
    public void testCreateDuplicateCourier() {
        String body = "{ \"login\": \"" + courierLogin + "\", \"password\": \"" + courierPassword + "\", \"firstName\": \"" + courierName + "\" }";
        givenRequest()
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(409); // ожидается конфликт
    }

    @Test
    @Step("Метод POST to /api/v1/courier - если одного из полей нет, запрос возвращает ошибку")
    public void testCreateCourierMissingFields() {
        String body = "{ \"login\": \"" + System.currentTimeMillis() + "\" }";
        givenRequest()
                .body(body)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(400);
    }
    public int courierId;
    @Step("Удаление курьера")
    public Response deleteCourier(int courierId) {
        String COURIER_PATH = "/api/v1/courier";
        return givenRequest()
                .header("Content-type", "application/json")
                .when()
                .delete(COURIER_PATH + "/" + courierId);
    }

    @After
    public void tearDown() {
        if (courierId != 0) {
           deleteCourier(courierId);
        }
    }
}
