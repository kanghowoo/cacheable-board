package com.mide;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BoardConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BoardConsumerApplication.class);
    }
}
