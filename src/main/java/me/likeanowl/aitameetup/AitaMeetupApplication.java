package me.likeanowl.aitameetup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class AitaMeetupApplication {
    public static void main(String[] args) {
        SpringApplication.run(AitaMeetupApplication.class, args);
    }
}

