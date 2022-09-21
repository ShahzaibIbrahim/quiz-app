package com.tt.quizbuilder.dao;

import com.tt.quizbuilder.entity.User;
import com.tt.quizbuilder.entity.UserAuthentication;

public interface UserDAO {

	public User findById(int theId);
	
	public void save(User user);

	public User findByEmail(String email);

	public String saveAuthentication(UserAuthentication authentication);
	public UserAuthentication validateToken(String authorizationKey);

	public UserAuthentication getAuthenticationByToken(String authorizationKey);
}
