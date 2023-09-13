package ru.example.main.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import ru.example.fake.module.configuration.FakeModuleConfiguration;

@Configuration
@Import(value = {FakeModuleConfiguration.class})
public class AppConfiguration {

}
