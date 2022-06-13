package com.example.springbootrestapp;

import com.example.springbootrestapp.entity.User;
import com.example.springbootrestapp.retrofit.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RetrofitUsersControllerTest {

    @RegisterExtension
    static Service service = new Service();

    @Test
    void getUsers() throws IOException {
        Response<List<User>> response = service.get().getUsers().execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).hasSizeGreaterThanOrEqualTo(1);
    }

    @Test
    void getUser() throws IOException {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();

        Response<User> response = service.get().getUser(1L).execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isEqualTo(user);
    }

    @ParameterizedTest
    @ValueSource(longs = {-1, 0, Integer.MAX_VALUE})
    void getUserNotFound(long id) throws IOException {
        Response<User> response = service.get().getUser(id).execute();
        assertThat(response.code()).isEqualTo(404);
    }

    @Test
    void postUser() throws IOException {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();

        Response<User> response = service.get().addUser(user).execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isEqualTo(user);
    }

    @Test
    void putUser() throws IOException {
        User user = User.builder()
                .id(1)
                .firstName("John")
                .lastName("Parker")
                .email("jp@email.com")
                .build();
        Response<User> response = service.get().updateUser(1L, user).execute();
        assertThat(response.isSuccessful()).isTrue();
        assertThat(response.body()).isEqualTo(user);
    }
}