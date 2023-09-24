package com.goalddae;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// 크롤링 예약 시간 설정
@EnableScheduling
public class GoalddaeApplication {
	public static void main(String[] args) {
		SpringApplication.run(GoalddaeApplication.class, args);
	}
}
