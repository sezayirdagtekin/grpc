package com.shopping.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hibernate.Session;

import com.shopping.db.H2DatabaseConnection;
import com.shopping.entity.Order;

import io.grpc.InternalChannelz.ChannelTrace.Event.Severity;

public class OrderDao {

	private static final String SQL = "From Order Where userId=:userId";

	private static final Logger logger = Logger.getLogger(OrderDao.class.getName());

	public List<Order> getOrdersForUsers(int userId) {
		try {
			Session session = H2DatabaseConnection.getHibernateSession();
			return session.createQuery(SQL, Order.class).setParameter("userId", userId).getResultList();

		} catch (Exception e) {
			logger.log(Level.SEVERE, "Could not execute query");
		}
		return null;

	}

}
