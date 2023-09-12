package ru.example.main;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import ru.example.fake.module.service.FirstServiceBean;
import ru.example.fake.module.service.SecondServiceBean;
import ru.example.fake.module.service.ThirdServiceBean;

/**
 * Класс тестов, проверяющих работу различных способов исключения бина при поднятии контекста.
 *
 */
@SpringBootTest
public class FakeBeanExistTest {

    @Autowired
    private ApplicationContext context;

    @Test
    void testExcludeBeanFromContextByComponentScanFilter() {
        NoSuchBeanDefinitionException exception = Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean("firstServiceBean", FirstServiceBean.class),
                "Bean with name \'firstServiceName\' has been added to ApplicationContext");
    }

    @Test
    void testExcludeBeanFromContextByBeanFactoryPostProcessor() {
        NoSuchBeanDefinitionException exception = Assertions.assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean("secondServiceBean", SecondServiceBean.class),
                "Bean with name \'secondServiceName\' has been added to ApplicationContext");
    }

    @Test
    void testBeanFromDependencyExist() {
        ThirdServiceBean bean = context.getBean("thirdServiceBean", ThirdServiceBean.class);
        Assertions.assertNotNull(bean,
                "Bean with name \'thirdServiceBean\' has not been added to ApplicationContext");
        Assertions.assertInstanceOf(ThirdServiceBean.class, bean,
                "Bean with name \'thirdServiceBean\' has been added to ApplicationContext, but with another type");
    }
}
