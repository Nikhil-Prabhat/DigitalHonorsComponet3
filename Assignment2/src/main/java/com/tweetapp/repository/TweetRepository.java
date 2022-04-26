package com.tweetapp.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.tweetapp.model.TweetDetails;

@Repository
public class TweetRepository {
	
	@Autowired
	private DynamoDBMapper mapper;
	
	public void save(TweetDetails tweetDetails)
	{
		mapper.save(tweetDetails);
	}

	public TweetDetails findByUsername(String username)
	{
		TweetDetails tweetDetails = mapper.load(TweetDetails.class,username);
		return tweetDetails;
	}
	
	public List<TweetDetails> findAll()
	{
		List<TweetDetails> tweetDetailsList = new ArrayList<>();
		PaginatedScanList<TweetDetails> scanList = mapper.scan(TweetDetails.class, new DynamoDBScanExpression());
		for(TweetDetails tweetDetails : scanList)
		{
			tweetDetailsList.add(tweetDetails);
		}
		
		return tweetDetailsList;
	}
}
