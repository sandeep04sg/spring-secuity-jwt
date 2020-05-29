package com.spring.security.jwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;

@SpringBootApplication
public class SpringSecurityJwt {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwt.class, args);
    }

}
