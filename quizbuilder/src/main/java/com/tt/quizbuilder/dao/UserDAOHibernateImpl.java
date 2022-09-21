package com.tt.quizbuilder.dao;

import com.tt.quizbuilder.entity.User;
import com.tt.quizbuilder.entity.UserAuthentication;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

@Repository
public class UserDAOHibernateImpl implements UserDAO {

	// define field for entitymanager	
	private EntityManager entityManager;
		
	// set up constructor injection
	@Autowired
	public UserDAOHibernateImpl(EntityManager theEntityManager) {
		entityManager = theEntityManager;
	}

	@Override
	public User findById(int theId) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// get the User
		User theUser =
				currentSession.get(User.class, theId);
		
		// return the User
		return theUser;
	}


	@Override
	public void save(User theUser) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		
		// save User
		currentSession.saveOrUpdate(theUser);
	}

	@Override
	public User findByEmail(String email) {

		// get the current hibernate session
		Session currentSession = entityManager.unwrap(Session.class);
		User user;
		Query<User> theQuery = currentSession.createQuery("from User where email=:email");
		theQuery.setParameter("email", email);

		try {
			user = theQuery.getSingleResult();
		} catch (NoResultException noResultException) {
			user = null;
		}

		return user;
	}

	@Override
	public String saveAuthentication(UserAuthentication authentication) {
		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(authentication);

		return authentication.getAuthToken();
	}

	@Override
	public UserAuthentication validateToken(String authorizationKey) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<UserAuthentication> theQuery = currentSession.createQuery("from UserAuthentication where authToken=:authToken and sessionActive=true");
		theQuery.setParameter("authToken", authorizationKey);

		UserAuthentication authentication;

		try {
			authentication = theQuery.getSingleResult();
		} catch (NoResultException noResultException) {
			authentication = null;
		}

		return authentication;
	}

	@Override
	public UserAuthentication getAuthenticationByToken(String authorizationKey) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query<UserAuthentication> theQuery = currentSession.createQuery("from UserAuthentication where authToken=:authToken");
		theQuery.setParameter("authToken", authorizationKey);
		UserAuthentication authentication;

		try {
			authentication = theQuery.getSingleResult();
		} catch (NoResultException noResultException) {
			authentication = null;
		}

		return authentication;
	}


}







