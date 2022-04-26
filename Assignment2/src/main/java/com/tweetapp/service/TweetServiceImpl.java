package com.tweetapp.service;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tweetapp.model.Tweet;
import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;
import com.tweetapp.repository.TweetRepository;
import com.tweetapp.repository.UserRepository;

@Service
public class TweetServiceImpl implements TweetService {

	@Autowired
	private TweetRepository tweetRepository;

	@Autowired
	private UserRepository userRepository;

	@Override
	public String registerUser(String username, String password) {

		if (username != null && password != null) {
			UserDetails userDetails = new UserDetails(username, password);
			userRepository.save(userDetails);
			return TweetAppMessages.USER_REGISTRATION_SUCCESSFUL.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public String login(String username, String password) {
		if (username != null && password != null) {
			UserDetails userDetails = getUserDetails(username, password);
			if (userDetails != null)
				return TweetAppMessages.LOGIN_SUCCESSFUL.getMessage();
			else
				return TweetAppMessages.INVALID_CREDENTIALS.getMessage();

		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public String forgetPassword(String username, String newPassword) {
		if (username != null && newPassword != null) {
			UserDetails findByUsername = getUserByUsername(username);
			if (findByUsername != null) {
				findByUsername.setPassword(newPassword);
				userRepository.save(findByUsername);
				return TweetAppMessages.PASSWORD_RESET.getMessage();

			} else
				return TweetAppMessages.INVALID_USERNAME.getMessage();

		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	@Override
	public List<TweetDetails> getAllTweets() {
		 return tweetRepository.findAll();
	}

	@Override
	public List<UserDetails> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public UserDetails getUserByUsername(String username) {
		UserDetails findByUsername = userRepository.findByUsername(username);
		return findByUsername;
	}

	@Override
	public List<String> tweetsOfSpecificUser(String username) {
		List<String> tweetList = new ArrayList<String>();

		List<Tweet> tweetsOfSpecificUser = getTweetListPerUser(username);
		for (Tweet tweet : tweetsOfSpecificUser) {
			tweetList.add(tweet.getTweet());
		}

		return tweetList;

	}

	@Override
	public String postNewTweet(String username, String tweet) {
		if (username != null && tweet != null) {
			List<Tweet> tweetList = getTweetListPerUser(username);
			Tweet newTweet = new Tweet();
			newTweet.setTweet(tweet);
			tweetList.add(newTweet);
			TweetDetails tweetDetails = new TweetDetails(username, tweetList);
			tweetRepository.save(tweetDetails);
			return TweetAppMessages.NEW_POST_SUCCESSFUL.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	/*
	 * id is negated to handle 0 based indexing
	 */

	@Override
	public String updateTweet(String username, int id, String tweet) {
		--id;
		if (username != null && tweet != null) {
			List<Tweet> tweetList = getTweetListPerUser(username);
			Tweet tweets = tweetList.get(id);
			tweets.setTweet(tweet);
			tweetList.set(id, tweets);
			TweetDetails tweetDetails = new TweetDetails(username, tweetList);
			tweetRepository.save(tweetDetails);
			return TweetAppMessages.TWEET_UPDATED.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();

	}

	@Override
	public String deleteTweet(String username, int id) {
		--id;
		if (username != null) {
			List<Tweet> tweetList = getTweetListPerUser(username);
			tweetList.remove(id);
			TweetDetails tweetDetails = new TweetDetails(username, tweetList);
			tweetRepository.save(tweetDetails);
			return TweetAppMessages.TWEET_DELETED.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();

	}

	@Override
	public String likeTweet(String username, int id) {
		--id;
		if (username != null) {
			List<Tweet> tweetList = getTweetListPerUser(username);
			Tweet tweet = tweetList.get(id);
			tweet.setLiked(Boolean.TRUE);
			tweetList.set(id, tweet);
			TweetDetails tweetDetails = new TweetDetails(username, tweetList);
			tweetRepository.save(tweetDetails);
			return TweetAppMessages.TWEET_LIKED.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();

	}

	@Override
	public String replyTweet(String username, int id, String tweet) {
		--id;
		List<String> repliedTweets;
		if (username != null && tweet != null) {
			List<Tweet> tweetList = getTweetListPerUser(username);
			Tweet tweetInfo = tweetList.get(id);
			repliedTweets = tweetInfo.getRepliedTweets();
			if (repliedTweets != null)
				repliedTweets.add(tweet);
			else {
				repliedTweets = new ArrayList<>();
				repliedTweets.add(tweet);
			}

			tweetInfo.setRepliedTweets(repliedTweets);
			tweetList.set(id, tweetInfo);
			TweetDetails tweetDetails = new TweetDetails(username, tweetList);
			tweetRepository.save(tweetDetails);
			return TweetAppMessages.TWEET_REPLIED.getMessage();
		} else
			return TweetAppMessages.INVALID_INPUT.getMessage();
	}

	private UserDetails getUserDetails(String username, String password) {
		UserDetails userDetails = userRepository.findByUsernameAndPassword(username, password);
		return userDetails;
	}

	private List<Tweet> getTweetListPerUser(String username) {
		TweetDetails findByUsername = tweetRepository.findByUsername(username);
		List<Tweet> tweetList = new ArrayList<>();
		if (findByUsername != null) {
			tweetList = findByUsername.getTweets();
		} else
			tweetList = new ArrayList<>();
		return tweetList;
	}

}
