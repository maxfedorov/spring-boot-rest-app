package com.example.springbootrestapp.restassured;

import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import static io.restassured.RestAssured.with;

public class Spec implements BeforeAllCallback {
    static RequestSpecification spec;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        spec = with()
                .baseUri("http://localhost:8080")
                .basePath("/api/v1");
    }

    public RequestSpecification get() {
        return spec;
    }
}
