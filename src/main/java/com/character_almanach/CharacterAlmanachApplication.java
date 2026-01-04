package com.character_almanach;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.character_almanach")
public class CharacterAlmanachApplication {

	public static void main(String[] args) {
		SpringApplication.run(CharacterAlmanachApplication.class, args);
	}

}
