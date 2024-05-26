package de.PSWTM.DigitalForms;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class DigitalFormsApplicationTests {

	@Test
	void contextLoads() {
	}

	
	@Configuration
	static class TestConfig {
		@Bean
		public MongoClient mongoClient() {
			return MongoClients.create("mongodb://localhost:27017");
		}

		@Bean
		public MongoTemplate mongoTemplate() {
			return new MongoTemplate(mongoClient(), "test");
		}
	}

}
