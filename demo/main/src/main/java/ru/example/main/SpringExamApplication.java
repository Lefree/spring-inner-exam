package ru.example.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.example.fake.module.configuration.FirstServiceConfiguration;

@SpringBootApplication(exclude = {FirstServiceConfiguration.class})
public class SpringExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringExamApplication.class, args);
    }

}
