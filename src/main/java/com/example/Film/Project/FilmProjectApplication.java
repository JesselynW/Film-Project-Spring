package com.example.Film.Project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
//@EnableCaching
public class FilmProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmProjectApplication.class, args);
	}

}
