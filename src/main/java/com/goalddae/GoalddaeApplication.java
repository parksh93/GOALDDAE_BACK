package com.goalddae;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GoalddaeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoalddaeApplication.class, args);
	}
}
