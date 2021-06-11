package com.web.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.web.app.model.User;
import com.web.app.service.UserService;

@RestController
public class MainController {

	@Autowired
	private UserService userService;
	
	@PostMapping(value="/login")
	public ResponseEntity<?> login(@RequestBody User user) {
		
		if(userService.checkIfUserPresent(user)) {
			try {
				user = userService.login(user);
				return new ResponseEntity<>(user,HttpStatus.OK);
			} catch (Exception e) {
				
				return new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
			}
		}
		
		return new ResponseEntity<>("No such user",HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> registration(@RequestBody @Valid User user) {
		
		if (userService.checkIfUserPresent(user)) {
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} else {
			userService.save(user);
			return new ResponseEntity<>(HttpStatus.OK);
		}		
		
	}
	
	@GetMapping("/users")
	public List<User> getUsers() {
		
		return userService.showUsers();
	}
}
