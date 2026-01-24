package api.controllers;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static api.constant.CommonConstants.BASE_URI;
import static io.restassured.RestAssured.given;

public class FluentUserControllerWithRetry {
    RequestSpecification requestSpecification = given();

    public FluentUserControllerWithRetry() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.baseUri(BASE_URI);
        this.requestSpecification.filter(new AllureRestAssured());
    }
    @Step("Get user by name")
    public HttpResponse getUserWithRetry(String username) throws InterruptedException {
        Response response = given(this.requestSpecification).get(String.format("user/"+username));
        int statusCode = response.getStatusCode();
        if (200 != statusCode) {
            for (int i = 0; i<10; i++) {
                Thread.sleep(1000);
                response = given(this.requestSpecification).get(String.format("user/"+username));
                statusCode = response.getStatusCode();
                if (statusCode == 200) {
                    break;
                }
            }
        }
        return new HttpResponse(given(this.requestSpecification).get(String.format("user/"+username)).then());
    }
}
