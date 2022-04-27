package com.tweetapp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atLeastOnce;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.tweetapp.model.Tweet;
import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
class TweetServiceImplTest {

	@InjectMocks
	private TweetServiceImpl tweetServiceImpl;

	@Mock
	private UserRepository userRepository;
	
	@Mock
	private TweetRepository tweetRepository;

	private static final String USERNAME = "Nikhil.Prabhat@gmail.com";
	private static final String PASSWORD = "12345";
	private static final String TWEET = " New Tweet";
	
	@Test
	public void testRegisterUser() {
		Mockito.doNothing().when(userRepository).save(Mockito.any(UserDetails.class));
		String registerUser = tweetServiceImpl.registerUser(USERNAME, PASSWORD);
		assertEquals(registerUser, TweetAppMessages.USER_REGISTRATION_SUCCESSFUL.getMessage());
		Mockito.verify(userRepository, atLeastOnce()).save(Mockito.any(UserDetails.class));
	}
	
	@Test
	public void testRegisterUserInvalidInput() {
		Mockito.doNothing().when(userRepository).save(Mockito.any(UserDetails.class));
		String registerUser = tweetServiceImpl.registerUser(USERNAME, null);
		assertEquals(registerUser, TweetAppMessages.INVALID_INPUT.getMessage());
	}
	
	@Test
	public void testLoginSuccessful()
	{
		Mockito.when(userRepository.findByUsernameAndPassword(USERNAME, PASSWORD)).thenReturn(new UserDetails(USERNAME,PASSWORD));
		String login = tweetServiceImpl.login(USERNAME, PASSWORD);
		assertEquals(login, TweetAppMessages.LOGIN_SUCCESSFUL.getMessage());
		Mockito.verify(userRepository,atLeastOnce()).findByUsernameAndPassword(USERNAME, PASSWORD);
	}
	
	@Test
	public void testLoginInvalidCredentials()
	{
		Mockito.when(userRepository.findByUsernameAndPassword(USERNAME, PASSWORD)).thenReturn(null);
		String login = tweetServiceImpl.login(USERNAME, PASSWORD);
		assertEquals(login, TweetAppMessages.INVALID_CREDENTIALS.getMessage());
		Mockito.verify(userRepository,atLeastOnce()).findByUsernameAndPassword(USERNAME, PASSWORD);
	}
	
	@Test
	public void testLoginInvalidInput()
	{
		String login = tweetServiceImpl.login(null, PASSWORD);
		assertEquals(login, TweetAppMessages.INVALID_INPUT.getMessage());
	}

	@Test
	public void testForgetPassword()
	{
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(new UserDetails(USERNAME,PASSWORD));
		Mockito.doNothing().when(userRepository).save(Mockito.any(UserDetails.class));
		String forgetPassword = tweetServiceImpl.forgetPassword(USERNAME, PASSWORD);
		assertEquals(forgetPassword, TweetAppMessages.PASSWORD_RESET.getMessage());
		Mockito.verify(userRepository,atLeastOnce()).findByUsername(Mockito.anyString());
		Mockito.verify(userRepository, atLeastOnce()).save(Mockito.any(UserDetails.class));
	}
	
	@Test
	public void testForgetPasswordInvalidUsername()
	{
		Mockito.when(userRepository.findByUsername(Mockito.anyString())).thenReturn(null);
		Mockito.doNothing().when(userRepository).save(Mockito.any(UserDetails.class));
		String forgetPassword = tweetServiceImpl.forgetPassword(USERNAME, PASSWORD);
		assertEquals(forgetPassword, TweetAppMessages.INVALID_USERNAME.getMessage());
		Mockito.verify(userRepository,atLeastOnce()).findByUsername(Mockito.anyString());
	}
	
	@Test
	public void testForgetPasswordInvalidInput()
	{
		String forgetPassword = tweetServiceImpl.forgetPassword(USERNAME, null);
		assertEquals(forgetPassword, TweetAppMessages.INVALID_INPUT.getMessage());
	}
	
	@Test
	public void testGetAllUsers()
	{
		Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());
		List<UserDetails> allUsers = tweetServiceImpl.getAllUsers();
		assertEquals(allUsers, new ArrayList<>());
		assertEquals(allUsers.size(), 0);
		Mockito.verify(userRepository,atLeastOnce()).findAll();
	}
	
	@Test
	public void testGetAllTweets()
	{
		Mockito.when(tweetRepository.findAll()).thenReturn(new ArrayList<>());
		List<TweetDetails> allTweets = tweetServiceImpl.getAllTweets();
		assertEquals(allTweets.size(), 0);
		Mockito.verify(tweetRepository,atLeastOnce()).findAll();
	}
	
	@Test
	public void testTweetsOfSpecificUser()
	{
		TweetDetails tweetDetails = new TweetDetails(USERNAME,new ArrayList<>());
		tweetDetails.getTweets().add(new Tweet());
		Mockito.when(tweetRepository.findByUsername(Mockito.anyString())).thenReturn(tweetDetails);
		List<String> tweetsOfSpecificUser = tweetServiceImpl.tweetsOfSpecificUser(USERNAME);
		assertEquals(tweetsOfSpecificUser.size(), 1);
		Mockito.verify(tweetRepository,atLeastOnce()).findByUsername(Mockito.anyString());
	}
	
	@Test
	public void testPostNewTweet()
	{
		TweetDetails tweetDetails = new TweetDetails(USERNAME,new ArrayList<>());
		tweetDetails.getTweets().add(new Tweet());
		Mockito.doNothing().when(tweetRepository).save(Mockito.any(TweetDetails.class));
		Mockito.when(tweetRepository.findByUsername(Mockito.anyString())).thenReturn(tweetDetails);
		String postNewTweet = tweetServiceImpl.postNewTweet(USERNAME, TWEET);
		assertEquals(postNewTweet, TweetAppMessages.NEW_POST_SUCCESSFUL.getMessage());
		Mockito.verify(tweetRepository,atLeastOnce()).save(Mockito.any(TweetDetails.class));
		
	}
	
	@Test
	public void testPostNewTweetInvalidInput()
	{
		String postNewTweet = tweetServiceImpl.postNewTweet(null, TWEET);
		assertEquals(postNewTweet, TweetAppMessages.INVALID_INPUT.getMessage());
		
	}
	
	@Test
	public void testUpdateTweet()
	{
		TweetDetails tweetDetails = new TweetDetails(USERNAME,new ArrayList<>());
		tweetDetails.getTweets().add(new Tweet());
		Mockito.when(tweetRepository.findByUsername(Mockito.anyString())).thenReturn(tweetDetails);
		Mockito.doNothing().when(tweetRepository).save(Mockito.any(TweetDetails.class));
		String updateTweet = tweetServiceImpl.updateTweet(USERNAME, 1, TWEET);
		assertEquals(updateTweet, TweetAppMessages.TWEET_UPDATED.getMessage());
		Mockito.verify(tweetRepository,atLeastOnce()).save(Mockito.any(TweetDetails.class));
	}
	
	@Test
	public void testUpdateTweetInvalidInput()
	{
		String updateTweet = tweetServiceImpl.updateTweet(USERNAME, 1, null);
		assertEquals(updateTweet, TweetAppMessages.INVALID_INPUT.getMessage());
	}
	
	@Test
	public void testDeleteTweet()
	{
		TweetDetails tweetDetails = new TweetDetails(USERNAME,new ArrayList<>());
		tweetDetails.getTweets().add(new Tweet());
		Mockito.when(tweetRepository.findByUsername(Mockito.anyString())).thenReturn(tweetDetails);
		Mockito.doNothing().when(tweetRepository).save(Mockito.any(TweetDetails.class));
		String deleteTweet = tweetServiceImpl.deleteTweet(USERNAME, 1);
		assertEquals(deleteTweet, TweetAppMessages.TWEET_DELETED.getMessage());
		Mockito.verify(tweetRepository,atLeastOnce()).save(Mockito.any(TweetDetails.class));
	}
	
	@Test
	public void testDeleteTweetInvalidInput()
	{
		String deleteTweet = tweetServiceImpl.deleteTweet(null, 1);
		assertEquals(deleteTweet, TweetAppMessages.INVALID_INPUT.getMessage());
	}
	
	@Test
	public void testLikeTweet()
	{
		TweetDetails tweetDetails = new TweetDetails(USERNAME,new ArrayList<>());
		tweetDetails.getTweets().add(new Tweet());
		Mockito.when(tweetRepository.findByUsername(Mockito.anyString())).thenReturn(tweetDetails);
		Mockito.doNothing().when(tweetRepository).save(Mockito.any(TweetDetails.class));
		String likeTweet = tweetServiceImpl.likeTweet(USERNAME, 1);
		assertEquals(likeTweet, TweetAppMessages.TWEET_LIKED.getMessage());
		Mockito.verify(tweetRepository,atLeastOnce()).save(Mockito.any(TweetDetails.class));
	}
	
	@Test
	public void testLikeTweetInvalidInput()
	{
		String likeTweet = tweetServiceImpl.likeTweet(null, 1);
		assertEquals(likeTweet, TweetAppMessages.INVALID_INPUT.getMessage());
	}
	
	@Test
	public void testReplyTweet()
	{
		TweetDetails tweetDetails = new TweetDetails(USERNAME,new ArrayList<>());
		tweetDetails.getTweets().add(new Tweet());
		Mockito.when(tweetRepository.findByUsername(Mockito.anyString())).thenReturn(tweetDetails);
		Mockito.doNothing().when(tweetRepository).save(Mockito.any(TweetDetails.class));
		String replyTweet = tweetServiceImpl.replyTweet(USERNAME, 1, TWEET);
		assertEquals(replyTweet,TweetAppMessages.TWEET_REPLIED.getMessage());
		Mockito.verify(tweetRepository,atLeastOnce()).save(Mockito.any(TweetDetails.class));
		
	}
	
	@Test
	public void testReplyTweetInvalidInput()
	{
		String replyTweet = tweetServiceImpl.replyTweet(null, 1, TWEET);
		assertEquals(replyTweet,TweetAppMessages.INVALID_INPUT.getMessage());
		
	}
}
