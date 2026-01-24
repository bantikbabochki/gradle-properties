package api.controllers;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.specification.RequestSpecification;

import static api.constant.CommonConstants.BASE_URI;
import static api.testdata.TestDataStore.DEFAULT_ORDER;
import static io.restassured.RestAssured.given;

public class FluentStoreController {
    RequestSpecification requestSpecification = given();


    public FluentStoreController() {
        RestAssured.defaultParser = Parser.JSON;
        this.requestSpecification.contentType(ContentType.JSON);
        this.requestSpecification.accept(ContentType.JSON);
        this.requestSpecification.body(BASE_URI);
        this.requestSpecification.filter(new AllureRestAssured());
    }

    @Step("Add store order")
    public HttpResponse addStoreOrder() {
        this.requestSpecification.body(DEFAULT_ORDER);
        return new HttpResponse(given(this.requestSpecification).post("store/order").then());
    }

    @Step("Get order")
    public HttpResponse getStoreOrder(Integer id) {
        this.requestSpecification.body(id);
        return new HttpResponse(given(this.requestSpecification).get(String.format("store/order/" + id)).then());
    }

    @Step("Delete order")
    public HttpResponse deleteStoreOrder(Integer id) {
        this.requestSpecification.body(id);
        return new HttpResponse(given(this.requestSpecification).delete(String.format("store/order/" + id)).then());
    }
}
