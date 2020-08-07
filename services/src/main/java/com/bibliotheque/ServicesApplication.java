package com.bibliotheque;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.bibliotheque")
public class ServicesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicesApplication.class, args);
    }

}
