package com.mide;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import jakarta.annotation.PostConstruct;

@EnableEncryptableProperties
@SpringBootApplication(scanBasePackages = {"com.mide"})
public class BoardAppApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(BoardAppApplication.class, args);
	}

}
