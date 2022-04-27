package com.tweetapp.model;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class TweetDetailsTest {

	private static final String USERNAME = "Nikhil.Prabhat@gmail.com";

	TweetDetails tweetDetails = new TweetDetails(USERNAME, new ArrayList<>());

	@Test
	public void testGetUsername() {
		assertEquals(tweetDetails.getUsername(), USERNAME);
	}

	@Test
	public void testGetTweets() {
		assertEquals(tweetDetails.getTweets().size(), 0);
	}

}
