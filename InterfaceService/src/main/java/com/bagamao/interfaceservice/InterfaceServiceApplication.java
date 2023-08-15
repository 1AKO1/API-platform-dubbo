package com.bagamao.interfaceservice;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class InterfaceServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterfaceServiceApplication.class, args);
    }

}
