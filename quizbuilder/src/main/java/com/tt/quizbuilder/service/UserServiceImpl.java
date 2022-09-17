package com.tt.quizbuilder.service;

import com.tt.quizbuilder.dao.UserDAO;
import com.tt.quizbuilder.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private com.tt.quizbuilder.dao.UserDAO UserDAO;
	
	@Autowired
	public UserServiceImpl(UserDAO theUserDAO) {
		UserDAO = theUserDAO;
	}

	@Override
	@Transactional
	public User findById(int theId) {
		return UserDAO.findById(theId);
	}

	@Override
	@Transactional
	public void save(User theUser) {
		UserDAO.save(theUser);
	}

}






