package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestServiceApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(RestServiceApplication.class);
        app.setRegisterShutdownHook(false); // Disable the shutdown hook
        app.run(args);

        try {
            Thread.sleep(120000); // Sleep for 60 seconds (adjust the duration as needed)
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

}