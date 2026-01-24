package api.controllers;

import api.models.User;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import static api.constant.CommonConstants.BASE_URI;
import static api.testdata.TestData.DEFAULT_USER;
import static io.restassured.RestAssured.given;

public class FluentUserController {
    RequestSpecification requestSpecification = given();

    public FluentUserController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.baseUri(BASE_URI);
        this.requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Add default user")
    public HttpResponse addDefaultUser() {
        this.requestSpecification.body(DEFAULT_USER);
        return new HttpResponse(given(this.requestSpecification).post("user").then());
    }

    @Step("Add user")
    public HttpResponse addUser(User user) {
        this.requestSpecification.body(user);
        return new HttpResponse(given(this.requestSpecification).post("user").then());
    }

    @Step("Get user by name")
    public HttpResponse getUser(String username) {
        return new HttpResponse(given(this.requestSpecification).get(String.format("user/"+username)).then());
    }

    @Step("Delete user by name")
    public HttpResponse deleteUser(String username) {
        return new HttpResponse(given(this.requestSpecification).delete(String.format("user/"+username)).then());
    }
}
