package ru.example.fake.module.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import ru.example.fake.module.service.FirstServiceBean;

@Configuration
@ComponentScan(basePackages = {"ru.example.fake.module.service"},
        excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = FirstServiceBean.class))
public class FakeModuleConfiguration {

}
