package com.example.springbootrestapp.retrofit;

import com.example.springbootrestapp.entity.User;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface UsersService {

    @GET("api/v1/users")
    Call<List<User>> getUsers();

    @GET("api/v1/users/{id}")
    Call<User> getUser(@Path("id") Long id);

    @POST("api/v1/users")
    Call<User> addUser(@Body User item);

    @PUT("api/v1/users/{id}")
    Call<User> updateUser(@Path("id") Long id, @Body User item);

    @DELETE("api/v1/users/{id}")
    Call<User> deleteUser(@Path("id") Long id);
}
