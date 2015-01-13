/**
 * 
 */
package com.epam.training.persistence.oracledao;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.epam.training.persistence.pojo.HistoryTO;

/**
 * @author mr.zoom
 *
 */
public class RsToHistoryTO {
	/**
	 * @param rs
	 * @return history from resultset
	 * @throws SQLException
	 */
	public static HistoryTO returnTOFromRs(ResultSet rs) throws SQLException{
		HistoryTO to = null;
		String login = rs.getString("LOGIN");
		String name = rs.getString("FULL_NAME");
		double bid = rs.getDouble("BID");
		Integer userId = Integer.valueOf(rs.getInt("USER_ID"));
		to = new HistoryTO(login, name, bid, userId);
		return to;
		
	}
}
