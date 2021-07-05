package com.example.cardmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.example.*")
public class CardManagerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(CardManagerApplication.class, args);
    }

}
