package com.tweetapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

@Configuration
public class DynamoDBConfig {
	
	public static final String SERVICE_ENDPOINT = "dynamodb.us-east-1.amazonaws.com";
	public static final String REGION = "us-east-1";
	public static final String ACCESS_KEY = "AKIAYHABHQGQZBNRH7DO";
	public static final String SECRET_KEY = "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";
	
	@Bean
	public DynamoDBMapper mapper()
	{
		return new DynamoDBMapper(amazonDynamoDBConfig());
	}

	private AmazonDynamoDB amazonDynamoDBConfig() {
		return AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(SERVICE_ENDPOINT, REGION))
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
				.build();
	}

}
