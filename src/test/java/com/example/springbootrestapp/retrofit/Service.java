package com.example.springbootrestapp.retrofit;

import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Service implements BeforeAllCallback {
    static UsersService service;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        service = new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new AllureOkHttp3())
                        .build())
                .baseUrl("http://localhost:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsersService.class);
    }

    public UsersService get() {
        return service;
    }
}
