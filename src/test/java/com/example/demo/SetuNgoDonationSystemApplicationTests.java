package com.setu;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(
        classes = com.setu.SetuNgoDonationSystemApplication.class,
        properties = {
                "spring.datasource.url=jdbc:h2:mem:setu_db_test;MODE=MySQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
                "spring.datasource.username=sa",
                "spring.datasource.password=",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.jpa.hibernate.ddl-auto=create-drop",
                "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
        }
)
class SetuNgoDonationSystemApplicationTests {

	@Test
	void contextLoads() {
	}

}
