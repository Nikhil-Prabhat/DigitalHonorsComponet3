package com.tweetapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import com.tweetapp.model.UserDetails;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserRepositoryTest {

	@InjectMocks
	UserRepository userRepository;

	@Mock
	public DynamoDBMapper mapper;

	private static final String USERNAME = "Nikhil.Prabhat@gmail.com";
	private static final String PASSWORD = "12345";

	@Test
	public void testSave() {
		Mockito.doNothing().when(mapper).save(Mockito.any(UserDetails.class));
		userRepository.save(new UserDetails());
		Mockito.verify(mapper, atLeast(1)).save(Mockito.any(UserDetails.class));
	}

	@Test
	public void testFindByUsername() {
		Mockito.when(mapper.load(Mockito.any(), Mockito.anyString())).thenReturn(new UserDetails(USERNAME, PASSWORD));
		UserDetails findByUsername = userRepository.findByUsername(USERNAME);
		assertEquals(findByUsername.getUsername(), USERNAME);
		Mockito.verify(mapper, atLeastOnce()).load(Mockito.any(), Mockito.anyString());

	}

	@Test
	public void testFindByUsernameAndPassword() {
		Mockito.when(mapper.load(Mockito.any(), Mockito.anyString())).thenReturn(new UserDetails(USERNAME, PASSWORD));
		UserDetails findByUsernameAndPassword = userRepository.findByUsernameAndPassword(USERNAME, PASSWORD);
		assertEquals(findByUsernameAndPassword.getUsername(), USERNAME);
	}


}
