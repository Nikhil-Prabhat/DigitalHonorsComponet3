package com.tweetapp.service;

import static org.mockito.Mockito.atLeastOnce;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.test.context.junit4.SpringRunner;


@SpringBootTest
@RunWith(SpringRunner.class)
class TweetAppSQSServiceImplTest {
	
	@InjectMocks
	TweetAppSQSServiceImpl tweetAppSQSServiceImpl; 

	@Mock
	private QueueMessagingTemplate queueMessagingTemplate;
	
	private static final String MSG = "test message";
	
	
	@Test
	public void testSendMessageToQueue() {
		Mockito.doNothing().when(queueMessagingTemplate).send(Mockito.anyString(), Mockito.any());
		tweetAppSQSServiceImpl.sendMessageToQueue(MSG);
		Mockito.verify(queueMessagingTemplate, atLeastOnce()).send(Mockito.anyString(), Mockito.any());
	}

}
