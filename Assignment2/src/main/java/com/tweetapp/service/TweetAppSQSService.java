package com.tweetapp.service;

public interface TweetAppSQSService {
	
	public void sendMessageToQueue(String msg);

}
