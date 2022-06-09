package com.example.springbootrestapp;

import com.example.springbootrestapp.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.specification.RequestSpecification;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class UsersControllerTest {

    static RequestSpecification spec;

    @BeforeAll
    static void setup() {
        spec = with()
                .baseUri("http://localhost:8080")
                .basePath("/api/v1");
    }

    @Test
    void getUsers() {
        User[] users = spec
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .as(User[].class);
        assertThat(users).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void getUser() {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();
        User response = spec
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);
        assertThat(response).isEqualTo(user);
    }

    @Test
    void postUser() {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();
        User response = spec
                .contentType(JSON)
                .when()
                .body(asJson(user))
                .post("/users")
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);
        assertThat(response).isEqualTo(user);

    }

    @Test
    void putUser() {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();
        spec.contentType(JSON)
                .body(asJson(user))
                .when()
                .put("/users/1")
                .then()
                .statusCode(200)
                .body("id", is((int) user.getId()))
                .body("firstName", is(user.getFirstName()))
                .body("lastName", is(user.getLastName()))
                .body("email", is(user.getEmail()));
    }

    @SneakyThrows
    private static String asJson(Object object) {
        return new ObjectMapper().writeValueAsString(object);
    }
}