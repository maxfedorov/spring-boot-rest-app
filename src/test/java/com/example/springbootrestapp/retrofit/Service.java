package com.example.springbootrestapp.retrofit;

import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.springbootrestapp.config.Config.getPort;
import static java.lang.String.format;

public class Service implements BeforeAllCallback {
    static UsersService service;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        service = new Retrofit.Builder()
                .client(new OkHttpClient.Builder()
                        .addInterceptor(new AllureOkHttp3())
                        .build())
                .baseUrl(format("http://localhost:%s", getPort()))
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UsersService.class);
    }

    public UsersService get() {
        return service;
    }
}
