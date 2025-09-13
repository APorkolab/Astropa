package com.porkolab.chinesezodiac;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
		classes = ChinesezodiacApplication.class,
		properties = {
			"spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
		})
@ActiveProfiles("test")
class ChinesezodiacApplicationTests {

	@Test
	void contextLoads() {
		// This test ensures that the Spring context loads successfully
		// with security autoconfiguration disabled for testing
	}

}
