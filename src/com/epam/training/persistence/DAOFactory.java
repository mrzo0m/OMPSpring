/**
 * 
 */
package com.epam.training.persistence;

import com.epam.training.persistence.oracledao.OracleDAOFactory;

/**
 * @author Oleg_Burshinov
 * 
 */
public abstract class DAOFactory {

	/**
	 * @return User DAO object
	 */
	public abstract UserDAO getUserDAO();

	/**
	 * @return Item DAO object
	 */
	public abstract ItemDAO getItemDAO();

	/**
	 * @return Bids DAO obj
	 */
	public abstract BidsDAO getBidsDAO();
	
	/**
	 * @param vendor data base
	 * @return concrete db factory
	 */
	public static DAOFactory getDAOFactory(Vendors vendor) {
		DAOFactory df = null;
		switch (vendor) {
		case ORACLE: {
			df = OracleDAOFactory.getInstance();
		}
			break;
		default:
			df = null;
		}
		return df;

	}
}
