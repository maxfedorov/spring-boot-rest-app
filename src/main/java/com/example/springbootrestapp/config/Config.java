package com.example.springbootrestapp.config;

import java.io.IOException;
import java.util.Properties;

public class Config {

    private static final Properties PROPERTIES = loadProperties();

    public static String getPort() {
        return PROPERTIES.getProperty("server.port", "8080");
    }

    private static Properties loadProperties() {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("application.properties"));
            return properties;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
