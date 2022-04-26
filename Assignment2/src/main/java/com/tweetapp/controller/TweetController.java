package com.tweetapp.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tweetapp.model.Tweet;
import com.tweetapp.model.TweetDetails;
import com.tweetapp.model.UserDetails;
import com.tweetapp.service.TweetAppMessages;
import com.tweetapp.service.TweetAppSQSService;
import com.tweetapp.service.TweetService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1.0/tweets")
public class TweetController {

	Logger LOGGER = LoggerFactory.getLogger(TweetController.class);
	
	@Autowired
	private TweetService tweetServiceImpl;
	
	@Autowired
	private TweetAppSQSService tweetSQSServiceImpl;
	
	/*
	 * Send all logging data to SQS Queue
	 */

	@PostMapping("/register")
	public ResponseEntity<?> registerUser(@RequestBody UserDetails userDetails) {
		try {
			LOGGER.debug("Request resigterUser : {}", userDetails);
			tweetSQSServiceImpl.sendMessageToQueue("Request resigterUser : " + userDetails);
			String registerUser = tweetServiceImpl.registerUser(userDetails.getUsername(), userDetails.getPassword());
			if (registerUser.equalsIgnoreCase(TweetAppMessages.USER_REGISTRATION_SUCCESSFUL.getMessage())) {
				LOGGER.debug("Response registerUser : {}", registerUser);
				tweetSQSServiceImpl.sendMessageToQueue("Response resigterUser : " + registerUser);
				return new ResponseEntity<>(registerUser, HttpStatus.OK);
			} else {
				LOGGER.debug("Response registerUser : {}", registerUser);
				tweetSQSServiceImpl.sendMessageToQueue("Response resigterUser : " + registerUser);
				return new ResponseEntity<>(registerUser, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Response registerUser : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response resigterUser : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/login")
	public ResponseEntity<?> login(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		try {
			LOGGER.debug("Request login : {}", username + " " + password);
			tweetSQSServiceImpl.sendMessageToQueue("Request login : " + username + " " + password);
			String login = tweetServiceImpl.login(username, password);
			if (login.equalsIgnoreCase(TweetAppMessages.LOGIN_SUCCESSFUL.getMessage())) {
				LOGGER.debug("Response login : {}", login);
				tweetSQSServiceImpl.sendMessageToQueue("Response login : " + login);
				return new ResponseEntity<>(login, HttpStatus.OK);
			} else {
				LOGGER.debug("Response login : {}", login);
				tweetSQSServiceImpl.sendMessageToQueue("Response login : " + login);
				return new ResponseEntity<>(login, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Response login : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response login : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/{username}/forget")
	public ResponseEntity<?> forgetPassword(@PathVariable("username") String username,
			@RequestParam("password") String password) {
		try {
			LOGGER.debug("Request forgetPassword : {}", username + " " + password);
			tweetSQSServiceImpl.sendMessageToQueue("Request forgetPassword : " + username + " " + password);
			String forgetPassword = tweetServiceImpl.forgetPassword(username, password);
			if (forgetPassword.equalsIgnoreCase(TweetAppMessages.PASSWORD_RESET.getMessage())) {
				LOGGER.debug("Response forgetPassword : {}", forgetPassword);
				tweetSQSServiceImpl.sendMessageToQueue("Response forgetPassword : " + forgetPassword);
				return new ResponseEntity<>(forgetPassword, HttpStatus.OK);
			} else {
				LOGGER.debug("Response forgetPassword : {}", forgetPassword);
				tweetSQSServiceImpl.sendMessageToQueue("Response forgetPassword : " + forgetPassword);
				return new ResponseEntity<>(forgetPassword, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Response forgetPassword : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response forgetPassword : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/all")
	public ResponseEntity<?> getAllTweets() {
		try {
			LOGGER.debug("Request getAllTweets : {}");
			List<TweetDetails> allTweets = tweetServiceImpl.getAllTweets();
			tweetSQSServiceImpl.sendMessageToQueue("Response getAllTweets : " + allTweets);
			LOGGER.debug("Response getAllTweets : {}", allTweets);
			return new ResponseEntity<>(allTweets, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Response getAllTweets : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response getAllTweets : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/users/all")
	public ResponseEntity<?> getAllUsers() {
		try {
			LOGGER.debug("Request getAllUsers : {}");
			List<UserDetails> allUsers = tweetServiceImpl.getAllUsers();
			tweetSQSServiceImpl.sendMessageToQueue("Response getAllUsers : " + allUsers);
			LOGGER.debug("Response getAllUsers : {}", allUsers);
			return new ResponseEntity<>(allUsers, HttpStatus.OK);
		} catch (Exception e) {
			LOGGER.error("Response getAllUsers : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response getAllUsers : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/user/search/{username}")
	public ResponseEntity<?> searchByUsername(@PathVariable("username") String username) {
		try {
			LOGGER.debug("Request searchByUsername : {}", username);
			tweetSQSServiceImpl.sendMessageToQueue("Request searchByUsername : " + username);
			UserDetails userByUsername = tweetServiceImpl.getUserByUsername(username);
			if (userByUsername != null) {
				LOGGER.debug("Response searchByUsername : {}", userByUsername);
				tweetSQSServiceImpl.sendMessageToQueue("Response searchByUsername : " + userByUsername);
				return new ResponseEntity<>(userByUsername, HttpStatus.OK);
			} else {
				LOGGER.debug("Response searchByUsername : {}", userByUsername);
				tweetSQSServiceImpl.sendMessageToQueue("Response searchByUsername : " + userByUsername);
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Response searchByUsername : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response searchByUsername : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/{username}")
	public ResponseEntity<?> getAllTweetsPerUser(@PathVariable("username") String username) {
		try {
			LOGGER.debug("Request getAllTweetsPerUser : {}", username);
			tweetSQSServiceImpl.sendMessageToQueue("Request getAllTweetsPerUser :" + username);
			List<String> tweetsOfSpecificUser = tweetServiceImpl.tweetsOfSpecificUser(username);
			if (!tweetsOfSpecificUser.isEmpty()) {
				LOGGER.debug("Response getAllTweetsPerUser : {}", tweetsOfSpecificUser);
				tweetSQSServiceImpl.sendMessageToQueue
						("Response getAllTweetsPerUser :" + tweetsOfSpecificUser);
				return new ResponseEntity<>(tweetsOfSpecificUser, HttpStatus.OK);
			} else {
				LOGGER.debug("Response getAllTweetsPerUser : {}", tweetsOfSpecificUser);
				tweetSQSServiceImpl.sendMessageToQueue
						("Response getAllTweetsPerUser :" + tweetsOfSpecificUser);
				return new ResponseEntity<>(TweetAppMessages.NO_TWEETS_YET.getMessage(), HttpStatus.OK);
			}
		} catch (Exception e) {
			LOGGER.error("Response getAllTweetsPerUser {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response getAllTweetsPerUser :" + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/{username}/add")
	public ResponseEntity<?> postNewTweet(@PathVariable("username") String username, @RequestBody Tweet tweet) {
		try {
			LOGGER.debug("Request postNewTweet : {}", username + " " + tweet);
			tweetSQSServiceImpl.sendMessageToQueue("Request postNewTweet : " + username + " " + tweet);
			String postNewTweet = tweetServiceImpl.postNewTweet(username, tweet.getTweet());
			if (postNewTweet.equalsIgnoreCase(TweetAppMessages.NEW_POST_SUCCESSFUL.getMessage())) {
				LOGGER.debug("Response postNewTweet : {}", postNewTweet);
				tweetSQSServiceImpl.sendMessageToQueue("Response postNewTweet : " + postNewTweet);
				return new ResponseEntity<>(postNewTweet, HttpStatus.OK);
			} else {
				LOGGER.debug("Response postNewTweet : {}", postNewTweet);
				tweetSQSServiceImpl.sendMessageToQueue("Response postNewTweet : " + postNewTweet);
				return new ResponseEntity<>(postNewTweet, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Response postNewTweet : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response postNewTweet : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{username}/update/{id}")
	public ResponseEntity<?> updateTweet(@PathVariable("username") String username, @PathVariable("id") int id,
			@RequestParam("tweet") String tweet) {
		try {
			LOGGER.debug("Request updateTweet : {}", username + " " + id + " " + tweet);
			tweetSQSServiceImpl.sendMessageToQueue("Request updateTweet : " + username + " " + id + " " + tweet);
			String updateTweet = tweetServiceImpl.updateTweet(username, id, tweet);
			if (updateTweet.equalsIgnoreCase(TweetAppMessages.TWEET_UPDATED.getMessage())) {
				LOGGER.debug("Response updateTweet : {}", updateTweet);
				tweetSQSServiceImpl.sendMessageToQueue("Response updateTweet : " + updateTweet);
				return new ResponseEntity<>(updateTweet, HttpStatus.OK);
			} else {
				LOGGER.debug("Response updateTweet : {}", updateTweet);
				tweetSQSServiceImpl.sendMessageToQueue("Response updateTweet : " + updateTweet);
				return new ResponseEntity<>(updateTweet, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Response updateTweet : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response updateTweet : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@DeleteMapping("/{username}/delete/{id}")
	public ResponseEntity<?> deleteTweet(@PathVariable("username") String username, @PathVariable("id") int id) {
		try {
			LOGGER.debug("Request deleteTweet : {}", username + " " + id);
			tweetSQSServiceImpl.sendMessageToQueue("Request deleteTweet :" + username + " " + id);
			String deleteTweet = tweetServiceImpl.deleteTweet(username, id);
			if (deleteTweet.equalsIgnoreCase(TweetAppMessages.TWEET_DELETED.getMessage())) {
				LOGGER.debug("Response deleteTweet : {}", deleteTweet);
				tweetSQSServiceImpl.sendMessageToQueue("Response deleteTweet :" + deleteTweet);
				return new ResponseEntity<>(deleteTweet, HttpStatus.OK);
			} else {
				LOGGER.debug("Response deleteTweet : {}", deleteTweet);
				tweetSQSServiceImpl.sendMessageToQueue("Response deleteTweet :" + deleteTweet);
				return new ResponseEntity<>(deleteTweet, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Response deleteTweet : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response deleteTweet :" + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@PutMapping("/{username}/like/{id}")
	public ResponseEntity<?> likeTweet(@PathVariable("username") String username, @PathVariable("id") int id) {
		try {
			LOGGER.debug("Request likeTweet : {}", username + " " + id);
			tweetSQSServiceImpl.sendMessageToQueue("Request likeTweet : " + username + " " + id);
			String likeTweet = tweetServiceImpl.likeTweet(username, id);
			if (likeTweet.equalsIgnoreCase(TweetAppMessages.TWEET_LIKED.getMessage())) {
				LOGGER.debug("Response likeTweet : {}", likeTweet);
				tweetSQSServiceImpl.sendMessageToQueue("Response likeTweet : " + likeTweet);
				return new ResponseEntity<>(likeTweet, HttpStatus.OK);
			} else {
				LOGGER.debug("Response likeTweet : {}", likeTweet);
				tweetSQSServiceImpl.sendMessageToQueue("Response likeTweet : " + likeTweet);
				return new ResponseEntity<>(likeTweet, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Response likeTweet : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response likeTweet : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

	@PostMapping("/{username}/reply/{id}")
	public ResponseEntity<?> replyToTweet(@PathVariable("username") String username, @PathVariable("id") int id,
			@RequestParam("tweet") String tweet) {
		try {
			LOGGER.debug("Response replyToTweet : {}", username + " " + id + " " + tweet);
			tweetSQSServiceImpl.sendMessageToQueue("Response replyToTweet : " + username + " " + id + " " + tweet);
			String tweetReplied = tweetServiceImpl.replyTweet(username, id, tweet);
			if (tweetReplied.equalsIgnoreCase(TweetAppMessages.TWEET_REPLIED.getMessage())) {
				LOGGER.debug("Response replyToTweet : {}", tweetReplied);
				tweetSQSServiceImpl.sendMessageToQueue("Response replyToTweet : " + tweetReplied);
				return new ResponseEntity<>(tweetReplied, HttpStatus.OK);
			} else {
				LOGGER.debug("Response replyToTweet : {}", tweetReplied);
				tweetSQSServiceImpl.sendMessageToQueue("Response replyToTweet : " + tweetReplied);
				return new ResponseEntity<>(tweetReplied, HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			LOGGER.error("Response replyToTweet : {}", e.getMessage());
			tweetSQSServiceImpl.sendMessageToQueue("Response replyToTweet : " + e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}

}
