package com.web.app.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.web.app.dao.UserRepo;
import com.web.app.model.User;

@Service
public class UserService {

	@Autowired
	UserRepo repo;
	@Autowired
	BCryptPasswordEncoder encoder;
	
	public void save(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		repo.save(user);
	}
	
	public User login(User user) throws Exception{
		StringBuilder rawPasswd = new StringBuilder(user.getPassword());
		user = repo.findByUsername(user.getUsername());
		if (encoder.matches(rawPasswd, user.getPassword())) {
			user.setPassword(rawPasswd.toString());
			return user;
		} else {
			throw new Exception("Invalid Login Credentials");
		}
		
	}
	//checks if user data already present
	public boolean checkIfUserPresent(User user) {
		try {
			Optional.of(repo.findByUsername(user.getUsername())) ;
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	public List<User> showUsers() {
		return repo.findAll();
	}
}
