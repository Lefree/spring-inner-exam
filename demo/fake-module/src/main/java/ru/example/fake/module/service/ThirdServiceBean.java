package ru.example.fake.module.service;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class ThirdServiceBean {

    private static final Logger LOG = LogManager.getLogger(ThirdServiceBean.class);

    @PostConstruct
    public static void init() {
        LOG.info("Third Service Bean is created!");
    }

    public void print() {
        LOG.info("Third Service Bean execute method print!");
    }
}
