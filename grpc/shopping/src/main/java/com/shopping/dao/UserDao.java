package com.shopping.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.shopping.db.H2DatabaseConnection;
import com.shopping.entity.User;

public class UserDao {

	private static final String SQL = "select * from user where username=?";
	
	private static final Logger logger = Logger.getLogger(UserDao.class.getName());

	public User getUserDetail(String username) {

		User user = new User();

		try {
			Connection connection = H2DatabaseConnection.getConnectionToDatabase();
			PreparedStatement preparedStatement = connection.prepareStatement(SQL);
			preparedStatement.setString(1, username);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				user.setId(resultSet.getInt("id"));
				user.setUsername(resultSet.getString("username"));
				user.setName(resultSet.getString("name"));
				user.setAge(resultSet.getInt("age"));
				user.setGender(resultSet.getString("gender"));
			}
		} catch (SQLException exception) {
			logger.log(Level.SEVERE, "Could not execute query", exception);
		}
		return user;

	}

}
