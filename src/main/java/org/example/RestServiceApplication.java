package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class, UserDetailsServiceAutoConfiguration.class})
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestServiceApplication.class);
        app.setRegisterShutdownHook(false);
        app.run(args);

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}