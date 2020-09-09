package com.nettm.exercise.spring;

import com.nettm.config.MySQLAutoconfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAutoConfiguration(exclude = {MySQLAutoconfiguration.class})
@SpringBootApplication(scanBasePackages = {"com.nettm.exercise.spring"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
