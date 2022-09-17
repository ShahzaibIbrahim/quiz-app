package com.tt.quizbuilder.dao;

import com.tt.quizbuilder.entity.User;

public interface UserDAO {

	public User findById(int theId);
	
	public void save(User user);

}
