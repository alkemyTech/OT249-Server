package com.alkemy.ong;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles(value = {"test"})
@Tag("ut")
class OngApplicationTests {

	@Test
	void contextLoads() {
	}

}
