package com.mju.lighthouseai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class LightHouseAiApplication {

    public static void main(String[] args) {
        SpringApplication.run(LightHouseAiApplication.class, args);
    }

}
