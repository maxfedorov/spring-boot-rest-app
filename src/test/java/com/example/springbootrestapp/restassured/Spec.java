package com.example.springbootrestapp.restassured;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static io.restassured.RestAssured.with;

public class Spec implements BeforeEachCallback {
    static RequestSpecification spec;

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        spec = with()
                .given()
                .filter(new AllureRestAssured())
                .baseUri("http://localhost:8080")
                .basePath("/api/v1");
    }

    public RequestSpecification get() {
        return spec;
    }
}
