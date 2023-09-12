package ru.example.fake.module.service;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class FirstServiceBean {

    private static final Logger LOG = LogManager.getLogger(FirstServiceBean.class);

    @PostConstruct
    public static void init() {
        LOG.info("First Service Bean is created!");
    }

    public void print() {
        LOG.info("First Service Bean execute method print!");
    }
}
