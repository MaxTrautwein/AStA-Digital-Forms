package de.PSWTM.DigitalForms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DigitalFormsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DigitalFormsApplication.class, args);
	}

}
