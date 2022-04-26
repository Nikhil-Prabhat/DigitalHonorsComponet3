package com.tweetapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;


@Service
public class TweetAppSQSServiceImpl implements TweetAppSQSService {
	
	private static final String ENDPOINT = "https://sqs.us-east-1.amazonaws.com/564790788513/NikhilQueue";
	
	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Override
	public void sendMessageToQueue(String msg) {
		queueMessagingTemplate.send(ENDPOINT,MessageBuilder.withPayload(msg).build());		
	}

}
