package com.example.springbootrestapp;

import com.example.springbootrestapp.entity.User;
import com.example.springbootrestapp.restassured.Spec;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

public class RestAssuredUsersControllerTest {

    @RegisterExtension
    static Spec spec = new Spec();

    @Test
    void getUsers() {
        User[] users = spec.get()
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
        User response = spec.get()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .extract()
                .as(User.class);
        assertThat(response).isEqualTo(user);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, Integer.MAX_VALUE})
    void getUserNotFound(int id) {
        spec.get().when()
                .get(String.format("/users/%s", id))
                .then()
                .statusCode(404);
    }

    @Test
    void postUser() {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();
        User response = spec.get()
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
        spec.get().contentType(JSON)
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