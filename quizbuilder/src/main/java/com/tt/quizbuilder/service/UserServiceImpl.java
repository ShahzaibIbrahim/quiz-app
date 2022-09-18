package com.tt.quizbuilder.service;

import com.tt.quizbuilder.dao.UserDAO;
import com.tt.quizbuilder.entity.User;
import com.tt.quizbuilder.entity.UserAuthentication;
import com.tt.quizbuilder.exception.ProcessException;
import com.tt.quizbuilder.util.PasswordHashUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;

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

		User dbUser = UserDAO.findByUsername(theUser.getUsername());

		if(dbUser != null) {
			throw new ProcessException("Username already exist!");
		}

		String hashedPassword = PasswordHashUtil.generateHash(theUser.getPassword());
		theUser.setPassword(hashedPassword);
		UserDAO.save(theUser);
	}

	@Transactional
	public String generateToken(User user) {
		UserAuthentication userAuthentication = new UserAuthentication();

		userAuthentication.setCreateDate(new Date());
		userAuthentication.setUser(user);
		userAuthentication.setAuthToken(String.valueOf(UUID.randomUUID()));
		userAuthentication.setSessionActive(true);

		return UserDAO.saveAuthentication(userAuthentication);
	}
	@Transactional
	@Override
	public String loginUser(User user) {

		//1. Check if username and password matches
		User dbUser = UserDAO.findByUsername(user.getUsername());

		if(dbUser == null || !dbUser.getPassword().equalsIgnoreCase(PasswordHashUtil.generateHash(user.getPassword()))) {
			throw new ProcessException("Invalid Username or Password");
		}

		//2. generate the token
		return generateToken(dbUser);
	}

	@Transactional
	@Override
	public void logoutUser(String authorizationKey) {
		//1. get Authentication bean from db
		UserAuthentication userAuthentication =  UserDAO.getAuthenticationByToken(authorizationKey);

		//2. Mark session inactive
		userAuthentication.setSessionActive(false);

		//3. update in DB
		UserDAO.saveAuthentication(userAuthentication);
	}


}





