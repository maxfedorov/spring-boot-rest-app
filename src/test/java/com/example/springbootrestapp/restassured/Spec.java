package com.example.springbootrestapp.restassured;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.example.springbootrestapp.config.Config.getPort;
import static io.restassured.RestAssured.with;
import static java.lang.String.format;

public class Spec implements BeforeEachCallback {
    static RequestSpecification spec;

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        spec = with()
                .given()
                .filter(new AllureRestAssured())
                .baseUri(format("http://localhost:%s", getPort()))
                .basePath("/api/v1");
    }

    public RequestSpecification get() {
        return spec;
    }
}
