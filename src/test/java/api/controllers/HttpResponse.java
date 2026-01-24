package api.controllers;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

//Все методы, кроме getJsonValue(), возвращают this
//Это позволяет писать fluent-интерфейс:
public class HttpResponse {
    private final ValidatableResponse response;


    public HttpResponse(ValidatableResponse response) {
        this.response = response;
    }

    @Step("Check status code")
    public HttpResponse statusCodeIs(int status) {
        this.response.statusCode(status);
        return this;
    }

    @Step("Check json value by path '{path}' and expected value '{expectedValue}'")
    public HttpResponse jsonValueIs(String path, String expectedValue) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        assertThat(actualValue).isEqualTo(expectedValue);
        return this;
    }

    @Step("Check json value is null")
    public HttpResponse jsonValueIsNull(String path) {
        String actualValue = this.response.extract().jsonPath().getString(path);
        assertThat(actualValue).isNull();
        return this;
    }

    @Step("Get json value by path: {path}")
    public String getJsonValue(String path) {
        String value = this.response.extract().jsonPath().getString(path);
        assertThat(value).isNotNull();
        return value;
    }

    @Override
    @Step("Return info about response")
    //Возвращает читаемое представление ответа: статус-код + красиво отформатированный JSON
    public String toString() {
        return String.format("Status code: %s and response: \n%s",
                response.extract().response().statusCode(),
                response.extract().asPrettyString());
    }
}
