package com.tt.quizbuilder.service;

import com.tt.quizbuilder.entity.User;

public interface UserService {

	User findById(int theId);

	String registerUser(User theUser);

	String loginUser(User user);

	void logoutUser(String authorizationKey);
}
