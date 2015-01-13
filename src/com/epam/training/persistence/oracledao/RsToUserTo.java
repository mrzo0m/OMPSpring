/**
 * 
 */
package com.epam.training.persistence.oracledao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.training.persistence.pojo.UserTO;

/**
 * @author Oleg_Burshinov
 * 
 */
public class RsToUserTo {
	/**
	 * @param rs
	 * @return User info
	 * @throws SQLException
	 */
	public static UserTO returnTOFromRs(ResultSet rs) throws SQLException {
		UserTO to = null;
		Integer userId = Integer.valueOf(rs.getInt("USER_ID"));
		String fullName = rs.getString("FULL_NAME");
		String billingAddress = rs.getString("BILLING_ADDRESS");
		String login = rs.getString("LOGIN");
		String password = rs.getString("PASSWORD");
		String email = rs.getString("EMAIL");
		to = new UserTO(fullName, billingAddress, login, password, email);
		to.setUserId(userId);
		return to;

	}
}
