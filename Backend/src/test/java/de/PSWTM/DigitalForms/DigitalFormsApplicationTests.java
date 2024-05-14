package de.PSWTM.DigitalForms;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DigitalFormsApplicationTests {

	// Need to find a way to specifically disable the test that attempts to get a live Database Connection.
	// This is required as the DB is not active at compile time causing fails
	//@Test
	void contextLoads() {
	}

}
