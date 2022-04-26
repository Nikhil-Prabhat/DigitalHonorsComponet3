package com.tweetapp.model;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBDocument
public class Tweet {
	
	@DynamoDBAttribute
	private String tweet;
	
	@DynamoDBAttribute
	private boolean isLiked;
	
	@DynamoDBAttribute
	private List<String> repliedTweets;
}
