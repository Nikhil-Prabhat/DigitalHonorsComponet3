package com.tweetapp.service;

import java.util.List;

import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;

public interface TweetService {

	public String registerUser(String username, String password);

	public String login(String username, String password);

	public String forgetPassword(String username, String newPassword);

	public List<TweetDetails> getAllTweets();

	public List<UserDetails> getAllUsers();

	public UserDetails getUserByUsername(String username);

	public List<String> tweetsOfSpecificUser(String username);

	public String postNewTweet(String username, String tweet);

	public String updateTweet(String username, int id, String tweet);

	public String deleteTweet(String username, int id);

	public String likeTweet(String username, int id);

	public String replyTweet(String username, int id,String tweet);

}
