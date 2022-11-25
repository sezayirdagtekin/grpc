package com.shopping.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;
import org.hibernate.query.Query;

import com.shopping.db.H2DatabaseConnection;
import com.shopping.entity.User;

public class UserDao {

	private static final String SQL = "From User Where username=:username";

	private static final Logger logger = Logger.getLogger(UserDao.class.getName());

	public User getUserDetail(String username) {

		User user = new User();

		try {
			Session session = H2DatabaseConnection.getHibernateSession();
					
			Query<User> query = session.createQuery(SQL, User.class).setParameter("username", username);

			user = query.getSingleResult();

			user.setId(user.getId());
			user.setUsername(user.getUsername());
			//user.setPassword(user.getPassword());
			user.setName(user.getName());
			user.setAge(user.getAge());
			user.setGender(user.getGender());
			session.close();

		} catch (Exception exception) {
			logger.log(Level.SEVERE, "Could not execute query", exception);
		}

		return user;

	}

}
