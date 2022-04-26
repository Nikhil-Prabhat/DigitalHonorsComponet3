package com.tweetapp.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedScanList;
import com.tweetapp.model.UserDetails;

@Repository
public class UserRepository {

	@Autowired
	public DynamoDBMapper mapper;

	public void save(UserDetails userDetails) {
		mapper.save(userDetails);
	}

	public UserDetails findByUsername(String username) {
		UserDetails userDetails = mapper.load(UserDetails.class, username);
		return userDetails;
	}

	public UserDetails findByUsernameAndPassword(String username, String password) {
		UserDetails userDetails = mapper.load(UserDetails.class, username);
		if (Objects.nonNull(userDetails)) {

			// Case 1 : Correct Credentials
			// Case 2 : Invalid Credentials

			if (userDetails.getPassword().equals(password))
				return userDetails;
			else
				return null;
		}

		// When the userDetails is null
		return null;
	}

	public List<UserDetails> findAll() {
		List<UserDetails> userDetailsList = new ArrayList<>();
		PaginatedScanList<UserDetails> scanList = mapper.scan(UserDetails.class, new DynamoDBScanExpression());
		for (UserDetails userDetails : scanList)
			userDetailsList.add(userDetails);
		return userDetailsList;
	}

}
