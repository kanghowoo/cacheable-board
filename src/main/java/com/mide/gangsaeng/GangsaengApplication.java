package com.mide.gangsaeng;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableEncryptableProperties
public class GangsaengApplication {

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
	}

	public static void main(String[] args) {
		SpringApplication.run(GangsaengApplication.class, args);
	}

}
