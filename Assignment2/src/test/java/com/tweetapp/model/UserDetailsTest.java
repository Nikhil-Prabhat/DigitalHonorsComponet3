package com.tweetapp.model;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class UserDetailsTest {

	UserDetails userDetails = new UserDetails(USERNAME, PASSWORD);
	
	private static final String USERNAME = "Nikhil.Prabhat@gmail.com";
	private static final String PASSWORD = "12345";

	@Test
	public void testGetUsername() {
		assertEquals(userDetails.getUsername(), USERNAME);
	}

	@Test
	public void testGetPassword() {
		assertEquals(userDetails.getPassword(), PASSWORD);
	}

}
