package ru.example.fake.module.service;

import javax.annotation.PostConstruct;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;


@Component
public class SecondServiceBean {

    private static final Logger LOG = LogManager.getLogger(SecondServiceBean.class);

    @PostConstruct
    public static void init() {
        LOG.info("Second Service Bean is created!");
    }

    public void print() {
        LOG.info("Second Service Bean execute method print!");
    }
}
