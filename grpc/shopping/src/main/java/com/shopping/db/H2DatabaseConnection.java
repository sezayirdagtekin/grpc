package com.shopping.db;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.h2.tools.RunScript;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
public class H2DatabaseConnection {

	private static final Logger logger = Logger.getLogger(H2DatabaseConnection.class.getName());

	private static final SessionFactory sessionFactory = buildSessionFactory();

	static {
	initializeDatabase();
	}

	private static SessionFactory buildSessionFactory() {
		try {

			return new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		} catch (Throwable ex) {
			logger.log(Level.SEVERE, "build sessionFactory failed :" + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void close() {
		// Close all cached and active connection pools
		getSessionFactory().close();
	}

	public static Session getHibernateSession() {

		final SessionFactory sf = buildSessionFactory();

		// factory = new Configuration().configure().buildSessionFactory();
		final Session session = sf.openSession();
		return session;
	}

	    

	    /* Loads the initialize.sql file from the classpath folder "resources".
	    Runs all the queries from the file to create tables, insert records and make it ready to use
	    **/
		public static void initializeDatabase() {

			logger.info("Db initialization  is started...");
			try (InputStream is = H2DatabaseConnection.class.getClassLoader().getResourceAsStream("initialize.sql");) {
				
				Connection connection = getConnection();
				
				Transaction tx = getHibernateSession().beginTransaction();
				RunScript.execute(connection, new InputStreamReader(is));
				tx.commit();

			} catch (IOException ex) {
				logger.log(Level.SEVERE, " File not  found :" + ex);
			} catch (SQLException ex) {
				logger.log(Level.SEVERE, " Sql script execution is failed:" + ex);
			}
			logger.info("Db initialization  is completed...");
		}

		public static Connection getConnection() throws SQLException {
			Connection connection = sessionFactory
					               .getSessionFactoryOptions()
					               .getServiceRegistry()
					               .getService(ConnectionProvider.class)
					               .getConnection();
			return connection;
		}
	  
	 
}
