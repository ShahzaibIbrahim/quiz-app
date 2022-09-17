package com.tt.quizbuilder.service;

import com.tt.quizbuilder.entity.User;

public interface UserService {

	public User findById(int theId);
	
	public void save(User theUser);

	
}
