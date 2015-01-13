/**
 * 
 */
package com.epam.training.persistence.oracledao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Locale;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.epam.training.persistence.BidsDAO;
import com.epam.training.persistence.DAOFactory;
import com.epam.training.persistence.ItemDAO;
import com.epam.training.persistence.UserDAO;

/**
 * @author Oleg_Burshinov
 * 
 */
public class OracleDAOFactory extends DAOFactory {
	private static final Logger logger = Logger
			.getLogger(OracleDAOFactory.class);
	private volatile static OracleDAOFactory uniqueInstance;
	private DataSource ds;


	/**
	 * Oracle database DAO Implementation
	 */
	public OracleDAOFactory() {
		createPool();
	}

	/**
	 * @return Oracle DAO Factory
	 */
	public static OracleDAOFactory getInstance() {
		if (uniqueInstance == null) {
			synchronized (OracleDAOFactory.class) {
				if (uniqueInstance == null) {
					uniqueInstance = new OracleDAOFactory();
					logger.debug("Instance of OracleDaoFactory created.");
				}
			}
		}
		return uniqueInstance;
	}

	private void createPool() {
		Locale.setDefault(Locale.ENGLISH);
		try {
			Context initContext = null;
			Context envContext = null;
			try {
				initContext = new InitialContext();
				envContext = (Context) initContext.lookup("java:comp/env");
				ds =    (DataSource) envContext
						.lookup("jdbc/oralocal");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				logger.debug("Naming error...");
				e.printStackTrace();
			}
			if (envContext == null){
				logger.debug("Error: No Context");
				throw new Exception("Error: No Context");
			}
			if (ds == null){
				logger.debug("Error: No DataSource");
				throw new Exception("Error: No DataSource");
			}


		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	    Connection createConnection() {
		Connection con = null;
		try {
			con = ds.getConnection();
		} catch (SQLException e) {
			System.out.println("In create conn: ");
			e.printStackTrace();
		}
		return con;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.training.persistence.DAOFactory#getItemDAO()
	 */
	@Override
	public ItemDAO getItemDAO() {
		return new OracleItemDAO(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.epam.training.persistence.DAOFactory#getUserDAO()
	 */
	@Override
	public UserDAO getUserDAO() {
		return new OracleUserDAO(this);
	}

	@Override
	public BidsDAO getBidsDAO() {
		return new OracleBidsDAO(this);
	}

}
