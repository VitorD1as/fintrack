package io.vitor.fintrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class FintrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(FintrackApplication.class, args);
	}

}
