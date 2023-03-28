package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtWithoutLogout {

    public static void main(String[] args) {
        SpringApplication.run(JwtWithoutLogout.class, args);
    }

}