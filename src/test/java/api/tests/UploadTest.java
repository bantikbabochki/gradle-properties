package api.tests;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class UploadTest {
    @Test
    void apiUploadTest() {
        // URL API для загрузки изображения
        String apiUrl = "https://petstore.swagger.io/v2/pet/1/uploadImage";
        // Создаем объект File, который указывает на изображение для загрузки
        // Путь к файлу
        File file = new File("src/test/resources/mvn.png");
        // Отправляем POST-запрос на API с изображением
        Response response =
                given()
                        .header("accept", "application/json")
                        .contentType("multipart/form-data")
                        .multiPart("file", file, "image/jpeg") // Указываем тип содержимого файла
                        .when()
                        .post(apiUrl)
                        .then()
                        .statusCode(200)  // Проверяем, что запрос успешен
                        .extract()
                        .response();
        // Выводим ответ сервера
        System.out.println("Response: " + response.asString());
    }
}
