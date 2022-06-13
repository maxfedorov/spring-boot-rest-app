package com.example.springbootrestapp;

import com.example.springbootrestapp.entity.User;
import com.example.springbootrestapp.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Named.of;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Epic("Users")
@Feature("Controller")
@Story("Spring MVC")
public class UsersControllerTest {

    @MockBean
    private UserRepository repository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Get list of users")
    void shouldReturnListOfUsers() throws Exception {
        List<User> users = asList(
                User.builder()
                        .id(1L)
                        .firstName(new Faker().address().firstName())
                        .lastName(new Faker().address().lastName())
                        .email(new Faker().internet().emailAddress())
                        .build(),
                User.builder()
                        .id(2L)
                        .firstName(new Faker().address().firstName())
                        .lastName(new Faker().address().lastName())
                        .email(new Faker().internet().emailAddress())
                        .build()
        );
        when(repository.findAll()).thenReturn(users);
        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(content().json(asJson(users)));
    }

    @Test
    @DisplayName("Get non existing user")
    void shouldReturn404ForUserNotFound() throws Exception {
        when(repository.findById(123L)).thenReturn(Optional.empty());
        mockMvc.perform(get("/api/v1/users/123"))
                .andExpect(status().isNotFound());
    }

    @ParameterizedTest(name = "Create user {0}")
    @MethodSource("args")
    void shouldCreateUser(User user) throws Exception {
        when(repository.save(user)).thenReturn(user);
        mockMvc.perform(post("/api/v1/users")
                .contentType(APPLICATION_JSON)
                .content(asJson(user)))
                .andExpect(status().isOk());
    }

    static Stream<Arguments> args() {
        return Stream.of(
                arguments(of("With all fields", User.builder()
                        .firstName(new Faker().address().firstName())
                        .lastName(new Faker().address().lastName())
                        .email(new Faker().internet().emailAddress())
                        .build())),
                arguments(of("Without firstname", User.builder()
                        .lastName(new Faker().address().lastName())
                        .email(new Faker().internet().emailAddress())
                        .build())),
                arguments(of("Without lastname", User.builder()
                        .firstName(new Faker().address().firstName())
                        .email(new Faker().internet().emailAddress())
                        .build())),
                arguments(of("Without email", User.builder()
                        .firstName(new Faker().address().firstName())
                        .lastName(new Faker().address().lastName())
                        .build()))
        );
    }

    @SneakyThrows
    private static String asJson(Object object) {
        return new ObjectMapper().writeValueAsString(object);
    }
}