package ru.example.fake.module.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.example.fake.module.service.FirstServiceBean;

@Configuration
public class FirstServiceConfiguration {

    @Bean
    public FirstServiceBean firstServiceBean() {
        return new FirstServiceBean();
    }
}
