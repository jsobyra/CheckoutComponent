package com.checkout;

import com.checkout.models.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        Properties.createProperties("application");
        SpringApplication.run(Application.class, args);
    }
}
