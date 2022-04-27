package com.tweetapp.repository;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atLeastOnce;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.tweetapp.model.TweetDetails;

@RunWith(SpringRunner.class)
@SpringBootTest
class TweetRepositoryTest {

	@InjectMocks
	TweetRepository tweetRepository;

	@Mock
	public DynamoDBMapper mapper;

	private static final String USERNAME = "Nikhil.Prabhat@gmail.com";

	@Test
	public void testSave() {
		Mockito.doNothing().when(mapper).save(Mockito.any(TweetDetails.class));
		tweetRepository.save(new TweetDetails());
		Mockito.verify(mapper, atLeast(1)).save(Mockito.any(TweetDetails.class));
	}

	@Test
	public void testFindByUsername() {
		Mockito.when(mapper.load(Mockito.any(), Mockito.anyString())).thenReturn(new TweetDetails());
		tweetRepository.findByUsername(USERNAME);
		Mockito.verify(mapper, atLeastOnce()).load(Mockito.any(), Mockito.anyString());

	}

}
