package com.tweetapp.config;

import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;

@Configuration
public class AwsSQSConfig {
	
	public static final String REGION = "us-east-1";
	public static final String ACCESS_KEY = "AKIAUV66CJ23ISIP72PQ";
	public static final String SECRET_KEY = "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";
	
	@Bean
	public QueueMessagingTemplate queueMessagingTemplate()
	{
		return new QueueMessagingTemplate(amazonSQSAsync());
	}

	@Bean
	@Primary
	public AmazonSQS amazonSQSAsync() {
		return AmazonSQSAsyncClientBuilder.standard()
				.withRegion(REGION)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
				.build();
	}

}
