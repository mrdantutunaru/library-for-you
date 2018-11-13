package com.demo.libraryProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.demo.libraryProject"})
public class LibraryProjectApp {

    public static void main(String[] args) {
        SpringApplication.run(LibraryProjectApp.class, args);
    }

}
