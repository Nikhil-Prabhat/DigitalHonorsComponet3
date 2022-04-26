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
	public static final String ACCESS_KEY = "AKIAYHABHQGQZBNRH7DO";
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
