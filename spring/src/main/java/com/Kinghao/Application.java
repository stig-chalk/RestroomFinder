package com.Kinghao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @SpringBootApplication
 * 1. @Configuration  //Define a configuration class
 * 2. @EnableAutoConfiguration spring boot  //Automatically configure the project according to the dependency of jar package
 * 3. @ComponentScan  //Spring automatically scans annotations on classes and injects them into the spring container
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {

        SpringApplication.run(Application.class,args);
    }

}
