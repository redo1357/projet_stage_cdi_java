package com.umanteam.dadakar.admin.front.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.umanteam.dadakar.admin.front.dto.Complaint;
import com.umanteam.dadakar.admin.front.dto.Rating;
import com.umanteam.dadakar.admin.front.dto.User;
import com.umanteam.dadakar.admin.front.service.interfaces.IUserService;

@Service
public class UserService implements IUserService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${user.path}")
	private String userPath;
	
	@Value("${rating.base}")
	private int ratingBase;
	
	@Value("${rating.minacceptable}")
	private int ratingMinAcceptable;
	
	@Override
	public List<User> findAll() {
		List<User> users = new ArrayList<>();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1M2MxMzgxYi1mMGMzLTQ0NjQtYmZmYy0wOGUwYmY4YTZkMDMiLCJzdWIiOiJ1c2VybmFtZTAiLCJpYXQiOjE1MDQ3Nzg5MjgsImV4cCI6MzAwMTUwNDc3ODkyOH0.R048wuBYFIRNvylyz1SoqIysxOvPK5q8ddwxSKxgCU2hfd2ROCJ6_UVM5sznisYrjUFSHNWg7sN_Rg_3aZKb6A");
		HttpEntity<List<User>> request = new HttpEntity<>(users, headers);
		ResponseEntity<List<User>> usersResponse = restTemplate.exchange(userPath, HttpMethod.GET, request, new ParameterizedTypeReference<List<User>>() {});
		return usersResponse.getBody();
	}
	
	@Override
	public User findById(String id) {
		User user = new User();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1M2MxMzgxYi1mMGMzLTQ0NjQtYmZmYy0wOGUwYmY4YTZkMDMiLCJzdWIiOiJ1c2VybmFtZTAiLCJpYXQiOjE1MDQ3Nzg5MjgsImV4cCI6MzAwMTUwNDc3ODkyOH0.R048wuBYFIRNvylyz1SoqIysxOvPK5q8ddwxSKxgCU2hfd2ROCJ6_UVM5sznisYrjUFSHNWg7sN_Rg_3aZKb6A");
		HttpEntity<User> userRequest = new HttpEntity<>(user, headers);
		ResponseEntity<User> userResponse = restTemplate.exchange(userPath + "/" + id, HttpMethod.GET, userRequest, User.class);
		user = userResponse.getBody();
		return user;
	}
	
	@Override
	public User findByAccountUsername(String username) {
		User user = new User();
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1M2MxMzgxYi1mMGMzLTQ0NjQtYmZmYy0wOGUwYmY4YTZkMDMiLCJzdWIiOiJ1c2VybmFtZTAiLCJpYXQiOjE1MDQ3Nzg5MjgsImV4cCI6MzAwMTUwNDc3ODkyOH0.R048wuBYFIRNvylyz1SoqIysxOvPK5q8ddwxSKxgCU2hfd2ROCJ6_UVM5sznisYrjUFSHNWg7sN_Rg_3aZKb6A");
		HttpEntity<User> userRequest = new HttpEntity<>(user, headers);
		ResponseEntity<User> userResponse = restTemplate.exchange(userPath + "/username:" + username, HttpMethod.GET, userRequest, User.class);
		user = userResponse.getBody();
		return user;
	}
	
	@Override
	public User update(User user) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", "eyJhbGciOiJIUzUxMiJ9.eyJqdGkiOiI1M2MxMzgxYi1mMGMzLTQ0NjQtYmZmYy0wOGUwYmY4YTZkMDMiLCJzdWIiOiJ1c2VybmFtZTAiLCJpYXQiOjE1MDQ3Nzg5MjgsImV4cCI6MzAwMTUwNDc3ODkyOH0.R048wuBYFIRNvylyz1SoqIysxOvPK5q8ddwxSKxgCU2hfd2ROCJ6_UVM5sznisYrjUFSHNWg7sN_Rg_3aZKb6A");
		HttpEntity<User> userRequest = new HttpEntity<>(user, headers);
		ResponseEntity<User> userResponse = restTemplate.exchange(userPath + "/update", HttpMethod.PUT, userRequest, User.class);
		user = userResponse.getBody();
		return user;
	}
	
	@Override
	public List<Complaint> getComplaint() {
		List<Complaint> complaints = new ArrayList<>();
		List<User> users = findAll();
		for(User user: users) {
			int counter = 0;
			int nbRatings = user.getRatings().size();
			if(nbRatings > ratingBase) for(Rating rating: user.getRatings()) if(rating.getValue() < ratingMinAcceptable) counter++;
			double ratio = counter / nbRatings;
			if(ratio > 0.5) complaints.add(new Complaint(user, nbRatings, ratio));
		}
		return complaints;
	}

}
