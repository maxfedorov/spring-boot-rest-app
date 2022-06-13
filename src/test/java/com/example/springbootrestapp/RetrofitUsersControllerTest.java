package com.example.springbootrestapp;

import com.example.springbootrestapp.entity.User;
import com.example.springbootrestapp.retrofit.Service;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.Assertions.assertThat;

@Epic("Users")
@Feature("API")
@Story("Retrofit")
public class RetrofitUsersControllerTest {

    @RegisterExtension
    static Service service = new Service();

    @Test
    @DisplayName("Get users")
    void getUsers() throws IOException {
        Response<List<User>> response = step("Send request to get users", () -> service.get().getUsers().execute());
        step("Assert that response is successful", () -> assertThat(response.isSuccessful()).isTrue());
        step("Assert that list of users is returned", () -> assertThat(response.body()).hasSizeGreaterThanOrEqualTo(1));
    }

    @Test
    @DisplayName("Get user by id")
    void getUser() throws IOException {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();

        Response<User> response = step("Send request to get user", () -> service.get().getUser(1L).execute());
        step("Assert that response is successful", () -> assertThat(response.isSuccessful()).isTrue());
        step("Assert that user is returned", () -> assertThat(response.body()).isEqualTo(user));
    }

    @ParameterizedTest(name = "get user {0}")
    @ValueSource(longs = {-1, 0, Integer.MAX_VALUE})
    void getUserNotFound(long id) throws IOException {
        Response<User> response = step("Send request to get user", () -> service.get().getUser(id).execute());
        step("Assert that response is Not found", () -> assertThat(response.code()).isEqualTo(404));
    }

    @Test
    @DisplayName("Create user")
    void postUser() throws IOException {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();

        Response<User> response = step("Send request to create user", () -> service.get().addUser(user).execute());
        step("Assert that response is successful", () -> assertThat(response.isSuccessful()).isTrue());
        step("Assert that user is returned", () -> assertThat(response.body()).isEqualTo(user));
    }

    @Test
    @DisplayName("Update user")
    void putUser() throws IOException {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();
        Response<User> response = step("Send request to update user", () -> service.get().updateUser(1L, user).execute());
        step("Assert that response is successful", () -> assertThat(response.isSuccessful()).isTrue());
        step("Assert that user is returned", () -> assertThat(response.body()).isEqualTo(user));
    }
}