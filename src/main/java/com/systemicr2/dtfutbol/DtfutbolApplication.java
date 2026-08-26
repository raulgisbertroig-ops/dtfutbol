package com.systemicr2.dtfutbol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DtfutbolApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtfutbolApplication.class, args);
	}

}
