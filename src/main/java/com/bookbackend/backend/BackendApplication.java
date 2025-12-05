package com.bookbackend.backend;

import com.bookbackend.backend.book.entity.User;
import com.bookbackend.backend.book.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
	@Bean
	CommandLineRunner initTestUser(UserRepository userRepository) {
		return args -> {
			if (userRepository.count() == 0) {
				User user = User.builder()
						.user_id("tester")
						.user_password("1234")
						.name("테스트유저")
						.build();
				userRepository.save(user);
			}
		};
	}

}
