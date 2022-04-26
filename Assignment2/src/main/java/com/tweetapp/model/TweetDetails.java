package com.tweetapp.model;

import java.util.List;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "TweetDetails")
public class TweetDetails {
	
	@DynamoDBHashKey(attributeName = "username")
	private String username;
	
	@DynamoDBAttribute
	private List<Tweet> tweets;

}
