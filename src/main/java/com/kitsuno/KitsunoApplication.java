package com.kitsuno;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class KitsunoApplication {

	public static void main(String[] args) {
		SpringApplication.run(KitsunoApplication.class, args);
	}

}
