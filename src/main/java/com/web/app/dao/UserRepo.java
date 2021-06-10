package com.web.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.app.model.User;

@Repository
public interface UserRepo extends JpaRepository<User, Integer>{

	public User findByUsername(String username);
}
