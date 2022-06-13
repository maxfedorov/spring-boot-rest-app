package com.example.springbootrestapp;

import com.example.springbootrestapp.entity.User;
import com.example.springbootrestapp.restassured.Spec;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.qameta.allure.Allure.step;
import static io.restassured.http.ContentType.JSON;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@Epic("Users")
@Feature("API")
@Story("Rest Assured")
public class RestAssuredUsersControllerTest {

    @RegisterExtension
    static Spec spec = new Spec();

    @Test
    @DisplayName("Get users")
    void getUsers() {
        User[] users = spec.get()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .as(User[].class);
        step("Assert that list of users is returned", () -> assertThat(users).hasSizeGreaterThanOrEqualTo(1));
    }

    @Test
    @DisplayName("Get user by id")
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
        step("Assert user in response", () -> assertThat(response).isEqualTo(user));
    }

    @ParameterizedTest(name = "Get user {0}")
    @ValueSource(ints = {-1, 0, Integer.MAX_VALUE})
    void getUserNotFound(int id) {
        spec.get().when()
                .get(String.format("/users/%s", id))
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("Create user")
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
        step("Assert user in response", () -> assertThat(response).isEqualTo(user));
    }

    @Test
    @DisplayName("Update user")
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