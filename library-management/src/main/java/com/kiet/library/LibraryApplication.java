package com.kiet.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(LibraryApplication.class);

    public static void main(String[] args) {
        ApplicationContext ac = SpringApplication.run(LibraryApplication.class, args);
        System.out.println(ac.getBeanDefinitionCount());
        String[] names = ac.getBeanDefinitionNames();
        for(int i=0;i<names.length;i++){
            System.out.println(names[i]);
        }
    }

    @Override
    public void run(String... args) {
        logger.info("Library Book Management System Started");
        logger.info("Loading Book Catalogue...");
        logger.info("Book Management REST API is Ready");
    }
}
