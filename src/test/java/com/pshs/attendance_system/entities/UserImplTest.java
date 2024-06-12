

package com.pshs.attendance_system.entities;

import com.pshs.attendance_system.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class UserImplTest {


	private static final Logger logger = LogManager.getLogger(UserImplTest.class);
	private final UserService userService;

	@Autowired
	public UserImplTest(UserService userService) {
		this.userService = userService;
	}

	@Test
	void contextLoad() {
		assert userService != null;
	}

	@Test
	void getAllUsersCount() {
		Map<String, Integer> hashMap = new HashMap<>();
		hashMap.put("count", userService.countAllUsers());
		logger.info(hashMap);
		logger.info("Count: {}", hashMap.get("count"));
		assert hashMap.get("count") != null;
		assert hashMap.get("count") == 1;
	}
}