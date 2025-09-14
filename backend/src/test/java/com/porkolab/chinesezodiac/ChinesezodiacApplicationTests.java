package com.porkolab.chinesezodiac;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE,
        classes = ChinesezodiacApplication.class,
        properties = {
                "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration"
        })
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ChinesezodiacApplicationTests {

        @Container
        static MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:10.5")
                        .withDatabaseName("astropa_test")
                        .withUsername("test")
                        .withPassword("testpass");

        @DynamicPropertySource
        static void overrideDataSourceProperties(DynamicPropertyRegistry registry) {
                registry.add("spring.datasource.url", mariaDB::getJdbcUrl);
                registry.add("spring.datasource.username", mariaDB::getUsername);
                registry.add("spring.datasource.password", mariaDB::getPassword);
        }

	@Test
	void contextLoads() {
		// This test ensures that the Spring context loads successfully
		// with security autoconfiguration disabled for testing
	}

}
