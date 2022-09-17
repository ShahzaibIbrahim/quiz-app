package com.tt.quizbuilder.dao;

import com.tt.quizbuilder.entity.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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

}







